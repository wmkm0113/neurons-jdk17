/*
 * Licensed to the Nervousync Studio (NSYC) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nervousync.database.neurons.connection;

import jakarta.annotation.Nonnull;
import org.nervousync.commons.Globals;
import org.nervousync.database.neurons.NeuronsDataSource;
import org.nervousync.database.neurons.statement.CachedStatement;
import org.nervousync.database.neurons.statement.impl.CachedCallableStatement;
import org.nervousync.database.neurons.statement.impl.CachedPreparedStatement;
import org.nervousync.utils.*;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Executor;

/**
 * <h2 class="en-US">Database connection implement class</h2>
 * <h2 class="zh-CN">数据库连接实现类</h2>
 *
 * @author Steven Wee	<a href="mailto:wmkm0113@Hotmail.com">wmkm0113@Hotmail.com</a>
 * @version $Revision: 1.0.0 $ $Date: Oct 21, 2019 12:18:57 $
 */
public final class NeuronsConnection implements PooledConnection, Connection {

	private transient final LoggerUtils.Logger logger = LoggerUtils.getLogger(this.getClass());

	/**
	 * <span class="en-US">Data source instance object</span>
	 * <span class="zh-CN">数据源实例对象</span>
	 */
	private final NeuronsDataSource dataSource;
	/**
	 * <span class="en-US">Database support transactional status</span>
	 * <span class="zh-CN">数据库支持事务状态值</span>
	 */
	private final boolean transactional;
	/**
	 * <span class="en-US">JDBC connection instance object</span>
	 * <span class="zh-CN">JDBC连接实例对象</span>
	 */
	private final Connection connection;
	/**
	 * <span class="en-US">Maximum size of prepared statement</span>
	 * <span class="zh-CN">查询分析器的最大缓存结果</span>
	 */
	private final int cachedLimitSize;
	/**
	 * <span class="en-US">Cached prepared statement mapping</span>
	 * <span class="zh-CN">缓存的查询分析器映射表</span>
	 */
	private final List<CachedStatement> cachedStatements;
	/**
	 * <span class="en-US">Registered event listener list of connection</span>
	 * <span class="zh-CN">注册的连接事件监听器</span>
	 */
	private final List<ConnectionEventListener> connectionEventListeners;
	/**
	 * <span class="en-US">Registered event listener list of statement</span>
	 * <span class="zh-CN">注册的查询事件监听器</span>
	 */
	private final List<StatementEventListener> statementEventListeners;
	/**
	 * <span class="en-US">Create time of current connection</span>
	 * <span class="zh-CN">连接建立的时间</span>
	 */
	private final long connectedTime;
	/**
	 * <span class="en-US">Last activated time of current connection</span>
	 * <span class="zh-CN">最后一次使用连接的时间</span>
	 */
	private long lastActiveTime;

	/**
	 * <h4 class="en-US">Constructor method for database connection implement class</h4>
	 * <h4 class="zh-CN">数据库连接实现类的构造方法</h4>
	 *
	 * @param dataSource      <span class="en-US">Data source instance object</span>
	 *                        <span class="zh-CN">数据源实例对象</span>
	 * @param connection      <span class="en-US">JDBC connection instance object</span>
	 *                        <span class="zh-CN">JDBC连接实例对象</span>
	 * @param transactional   <span class="en-US">Database support transactional status</span>
	 *                        <span class="zh-CN">数据库支持事务状态值</span>
	 * @param cachedLimitSize <span class="en-US">Maximum size of prepared statement</span>
	 *                        <span class="zh-CN">查询分析器的最大缓存结果</span>
	 */
	public NeuronsConnection(@Nonnull final NeuronsDataSource dataSource, @Nonnull final Connection connection,
	                         final boolean transactional, final int cachedLimitSize) {
		this.dataSource = dataSource;
		this.connection = connection;
		this.transactional = transactional;
		this.cachedLimitSize = cachedLimitSize;
		if (this.cachedLimitSize < 0) {
			this.cachedStatements = Collections.emptyList();
		} else {
			this.cachedStatements = new ArrayList<>();
		}
		this.connectionEventListeners = new ArrayList<>();
		this.statementEventListeners = new ArrayList<>();
		this.connectedTime = this.lastActiveTime = DateTimeUtils.currentUTCTimeMillis();
	}

	/**
	 * <h4 class="en-US">Getter method for create time of current connection</h4>
	 * <h4 class="zh-CN">连接建立的时间的Getter方法</h4>
	 *
	 * @return <span class="en-US">Create time of current connection</span>
	 * <span class="zh-CN">连接建立的时间</span>
	 */
	public long getConnectedTime() {
		return this.connectedTime;
	}

	/**
	 * <h4 class="en-US">Getter method for last activated time of current connection</h4>
	 * <h4 class="zh-CN">最后一次使用连接的时间的Getter方法</h4>
	 *
	 * @return <span class="en-US">Last activated time of current connection</span>
	 * <span class="zh-CN">最后一次使用连接的时间</span>
	 */
	public long getLastActiveTime() {
		return this.lastActiveTime;
	}

	/**
	 * <h4 class="en-US">Update last activated time of current connection</h4>
	 * <h4 class="zh-CN">更新当前连接的最后一次使用时间</h4>
	 */
	public void activeConnection() {
		this.lastActiveTime = DateTimeUtils.currentUTCTimeMillis();
	}

	/**
	 * <h4 class="en-US">Reset current connection</h4>
	 * <h4 class="zh-CN">重置当前连接</h4>
	 */
	public void reset() throws SQLException {
		this.connection.setAutoCommit(Boolean.TRUE);
		if (this.transactional) {
			this.connection.setTransactionIsolation(Connection.TRANSACTION_NONE);
		}
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#createStatement()
	 */
	@Override
	public Statement createStatement() throws SQLException {
		return this.connection.createStatement();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#prepareStatement(String)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return this.createStatement(KeyType.SQL_ONLY, sql, Globals.DEFAULT_VALUE_INT,
				Globals.DEFAULT_VALUE_INT, Globals.DEFAULT_VALUE_INT, Globals.DEFAULT_VALUE_INT,
				new int[0], new String[0], CachedPreparedStatement.class);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#prepareCall(String)
	 */
	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return this.createStatement(KeyType.CALL_ONLY, sql, Globals.DEFAULT_VALUE_INT,
				Globals.DEFAULT_VALUE_INT, Globals.DEFAULT_VALUE_INT, Globals.DEFAULT_VALUE_INT,
				new int[0], new String[0], CachedCallableStatement.class);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#nativeSQL(String)
	 */
	@Override
	public String nativeSQL(String sql) throws SQLException {
		return this.connection.nativeSQL(sql);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#setAutoCommit(boolean)
	 */
	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		this.connection.setAutoCommit(autoCommit);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#getAutoCommit()
	 */
	@Override
	public boolean getAutoCommit() throws SQLException {
		return this.connection.getAutoCommit();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#commit()
	 */
	@Override
	public void commit() throws SQLException {
		this.connection.commit();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#rollback()
	 */
	@Override
	public void rollback() throws SQLException {
		this.connection.rollback();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#isClosed()
	 */
	@Override
	public boolean isClosed() throws SQLException {
		return this.connection.isClosed();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#getMetaData()
	 */
	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return this.connection.getMetaData();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		try {
			this.connection.setReadOnly(readOnly);
		} catch (SQLException e) {
			if (this.logger.isDebugEnabled()) {
				this.logger.warn("Set_Read_Only_Error");
			}
		}
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() throws SQLException {
		return this.connection.isReadOnly();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#setCatalog(String)
	 */
	@Override
	public void setCatalog(String catalog) throws SQLException {
		this.connection.setCatalog(catalog);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#getCatalog()
	 */
	@Override
	public String getCatalog() throws SQLException {
		return this.connection.getCatalog();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#setTransactionIsolation(int)
	 */
	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		this.connection.setTransactionIsolation(level);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#getTransactionIsolation()
	 */
	@Override
	public int getTransactionIsolation() throws SQLException {
		return this.connection.getTransactionIsolation();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#getWarnings()
	 */
	@Override
	public SQLWarning getWarnings() throws SQLException {
		return this.connection.getWarnings();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#clearWarnings()
	 */
	@Override
	public void clearWarnings() throws SQLException {
		this.connection.clearWarnings();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#createStatement(int, int)
	 */
	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return this.connection.createStatement(resultSetType, resultSetConcurrency);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#prepareStatement(String, int, int)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return this.createStatement(KeyType.SQL_CONCURRENCY, sql, resultSetType, resultSetConcurrency,
				Globals.DEFAULT_VALUE_INT, Globals.DEFAULT_VALUE_INT,
				new int[0], new String[0], CachedPreparedStatement.class);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#prepareCall(String, int, int)
	 */
	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return this.createStatement(KeyType.CALL_CONCURRENCY, sql, resultSetType, resultSetConcurrency,
				Globals.DEFAULT_VALUE_INT, Globals.DEFAULT_VALUE_INT,
				new int[0], new String[0], CachedCallableStatement.class);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#getTypeMap()
	 */
	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return this.connection.getTypeMap();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#setTypeMap(Map)
	 */
	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		this.connection.setTypeMap(map);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#setHoldability(int)
	 */
	@Override
	public void setHoldability(int holdability) throws SQLException {
		this.connection.setHoldability(holdability);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#getHoldability()
	 */
	@Override
	public int getHoldability() throws SQLException {
		return this.connection.getHoldability();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#setSavepoint()
	 */
	@Override
	public Savepoint setSavepoint() throws SQLException {
		return this.connection.setSavepoint();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#setSavepoint(String)
	 */
	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		return this.connection.setSavepoint(name);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#rollback(Savepoint)
	 */
	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		this.connection.rollback(savepoint);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#releaseSavepoint(Savepoint)
	 */
	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		this.connection.releaseSavepoint(savepoint);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#createStatement(int, int, int)
	 */
	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#prepareStatement(String, int, int, int)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
	                                          int resultSetHoldability) throws SQLException {
		return this.createStatement(KeyType.SQL_HOLDABILITY, sql, resultSetType,
				resultSetConcurrency, resultSetHoldability, Globals.DEFAULT_VALUE_INT,
				new int[0], new String[0], CachedPreparedStatement.class);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#prepareCall(String, int, int, int)
	 */
	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
	                                     int resultSetHoldability) throws SQLException {
		return this.createStatement(KeyType.CALL_HOLDABILITY, sql, resultSetType,
				resultSetConcurrency, resultSetHoldability, Globals.DEFAULT_VALUE_INT,
				new int[0], new String[0], CallableStatement.class);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#prepareStatement(String, int)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return this.createStatement(KeyType.SQL_AUTO_GENERATED_KEYS, sql, Globals.DEFAULT_VALUE_INT,
				Globals.DEFAULT_VALUE_INT, Globals.DEFAULT_VALUE_INT, autoGeneratedKeys,
				new int[0], new String[0], CachedPreparedStatement.class);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#prepareStatement(String, int[])
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return this.createStatement(KeyType.SQL_COLUMN_INDEXES, sql, Globals.DEFAULT_VALUE_INT,
				Globals.DEFAULT_VALUE_INT, Globals.DEFAULT_VALUE_INT, Globals.DEFAULT_VALUE_INT,
				columnIndexes, new String[0], CachedPreparedStatement.class);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#prepareStatement(String, String[])
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return this.createStatement(KeyType.SQL_COLUMN_NAMES, sql, Globals.DEFAULT_VALUE_INT,
				Globals.DEFAULT_VALUE_INT, Globals.DEFAULT_VALUE_INT, Globals.DEFAULT_VALUE_INT,
				new int[0], columnNames, CachedPreparedStatement.class);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#createClob()
	 */
	@Override
	public Clob createClob() throws SQLException {
		return this.connection.createClob();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#createBlob()
	 */
	@Override
	public Blob createBlob() throws SQLException {
		return this.connection.createBlob();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#createNClob()
	 */
	@Override
	public NClob createNClob() throws SQLException {
		return this.connection.createNClob();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#createSQLXML()
	 */
	@Override
	public SQLXML createSQLXML() throws SQLException {
		return this.connection.createSQLXML();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#isValid(int)
	 */
	@Override
	public boolean isValid(int timeout) throws SQLException {
		return this.connection.isValid(timeout);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#setClientInfo(String, String)
	 */
	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		this.connection.setClientInfo(name, value);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#setClientInfo(Properties)
	 */
	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		this.connection.setClientInfo(properties);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#getClientInfo(String)
	 */
	@Override
	public String getClientInfo(String name) throws SQLException {
		return this.connection.getClientInfo(name);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#getClientInfo()
	 */
	@Override
	public Properties getClientInfo() throws SQLException {
		return this.connection.getClientInfo();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#createArrayOf(String, Object[])
	 */
	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return this.connection.createArrayOf(typeName, elements);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#createStruct(String, Object[])
	 */
	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return this.connection.createStruct(typeName, attributes);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#setSchema(String)
	 */
	@Override
	public void setSchema(String schema) throws SQLException {
		this.connection.setSchema(schema);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#getSchema()
	 */
	@Override
	public String getSchema() throws SQLException {
		return this.connection.getSchema();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#abort(Executor)
	 */
	@Override
	public void abort(Executor executor) throws SQLException {
		this.connection.abort(executor);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#setNetworkTimeout(Executor, int)
	 */
	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		this.connection.setNetworkTimeout(executor, milliseconds);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#getNetworkTimeout()
	 */
	@Override
	public int getNetworkTimeout() throws SQLException {
		return this.connection.getNetworkTimeout();
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#unwrap(Class)
	 */
	@Override
	public <T> T unwrap(Class<T> clazz) throws SQLException {
		throw new SQLException("CerebrateConnection is not a wrapper.");
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#isWrapperFor(Class)
	 */
	@Override
	public boolean isWrapperFor(Class<?> clazz) {
		return Boolean.FALSE;
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see PooledConnection#getConnection()
	 */
	@Override
	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see Connection#close()
	 */
	@Override
	public void close() throws SQLException {
		if (this.cachedLimitSize < this.cachedStatements.size()) {
			Iterator<CachedStatement> iterator = this.cachedStatements.iterator();
			while (iterator.hasNext()) {
				CachedStatement cachedStatement = iterator.next();
				cachedStatement.close();
				iterator.remove();
			}
		}
		this.dataSource.closeConnection(this);
	}

	/**
	 * <h4 class="en-US">Close current connection</h4>
	 * <h4 class="zh-CN">关闭当前连接</h4>
	 *
	 * @throws SQLException <span class="en-US">if a database access error occurs</span>
	 *                      <span class="zh-CN">如果发生数据库访问错误</span>
	 */
	public void closeConnection() throws SQLException {
		for (CachedStatement cachedStatement : this.cachedStatements) {
			try {
				cachedStatement.close();
				this.statementEventListeners.forEach(statementEventListener ->
						statementEventListener.statementClosed(
								new StatementEvent(this, (PreparedStatement) cachedStatement)));
			} catch (SQLException e) {
				this.statementEventListeners.forEach(statementEventListener ->
						statementEventListener.statementErrorOccurred(
								new StatementEvent(this, (PreparedStatement) cachedStatement)));
				throw e;
			}
		}
		try {
			this.connectionEventListeners.forEach(connectionEventListener ->
					connectionEventListener.connectionClosed(new ConnectionEvent(this)));
			this.connection.close();
		} catch (SQLException e) {
			this.connectionEventListeners.forEach(connectionEventListener ->
					connectionEventListener.connectionErrorOccurred(new ConnectionEvent(this)));
			throw e;
		}
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see PooledConnection#addConnectionEventListener(ConnectionEventListener)
	 */
	@Override
	public void addConnectionEventListener(ConnectionEventListener listener) {
		this.connectionEventListeners.add(listener);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see PooledConnection#removeConnectionEventListener(ConnectionEventListener)
	 */
	@Override
	public void removeConnectionEventListener(ConnectionEventListener listener) {
		this.connectionEventListeners.remove(listener);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see PooledConnection#addStatementEventListener(StatementEventListener)
	 */
	@Override
	public void addStatementEventListener(StatementEventListener listener) {
		this.statementEventListeners.add(listener);
	}

	/**
	 * (Non-Javadoc)
	 *
	 * @see PooledConnection#removeStatementEventListener(StatementEventListener)
	 */
	@Override
	public void removeStatementEventListener(StatementEventListener listener) {
		this.statementEventListeners.remove(listener);
	}

	/**
	 * <h2 class="en-US">Enumeration value of SQL command type</h2>
	 * <h2 class="zh-CN">SQL语句类型枚举值</h2>
	 */
	enum KeyType {
		/**
		 * Sql only key type.
		 */
		SQL_ONLY,
		/**
		 * Sql concurrency key type.
		 */
		SQL_CONCURRENCY,
		/**
		 * Sql holdability key type.
		 */
		SQL_HOLDABILITY,
		/**
		 * Sql auto generated key type.
		 */
		SQL_AUTO_GENERATED_KEYS,
		/**
		 * Sql column indexes key type.
		 */
		SQL_COLUMN_INDEXES,
		/**
		 * Sql column names key type.
		 */
		SQL_COLUMN_NAMES,
		/**
		 * Call only key type.
		 */
		CALL_ONLY,
		/**
		 * Call concurrency key type.
		 */
		CALL_CONCURRENCY,
		/**
		 * Call holdability key type.
		 */
		CALL_HOLDABILITY
	}

	/**
	 * <h4 class="en-US">Generate statement instance object</h4>
	 * <h4 class="zh-CN">生成查询分析器实例对象</h4>
	 *
	 * @param <T>                  <span class="en-US">Target instance object type</span>
	 *                             <span class="zh-CN">实例对象类型</span>
	 * @param keyType              <span class="en-US">Enumeration value of SQL command type</span>
	 *                             <span class="zh-CN">SQL语句类型枚举值</span>
	 * @param sql                  <span class="en-US">SQL command</span>
	 *                             <span class="zh-CN">SQL语句</span>
	 * @param resultSetType        <span class="en-US">Result set type code</span>
	 *                             <span class="zh-CN">结果集类型代码</span>
	 * @param resultSetConcurrency <span class="en-US">Result set data type code</span>
	 *                             <span class="zh-CN">结果集数据类型代码</span>
	 * @param resultSetHoldability <span class="en-US">Result set transactional type code</span>
	 *                             <span class="zh-CN">结果集事务类型代码</span>
	 * @param autoGeneratedKeys    <span class="en-US">Returns the flag for automatically generated key values</span>
	 *                             <span class="zh-CN">返回自动生成键值的标志</span>
	 * @param columnIndexes        <span class="en-US">Array of column indices that can be used to get the inserted row</span>
	 *                             <span class="zh-CN">可用于获取的插入行中的列索引数组</span>
	 * @param columnNames          <span class="en-US">Can be used to get an array of column names in the inserted row</span>
	 *                             <span class="zh-CN">可用于获取的插入行中的列名称数组</span>
	 * @param statementType        <span class="en-US">Target instance object type</span>
	 *                             <span class="zh-CN">实例对象类型</span>
	 * @return <span class="en-US">Generated statement instance object</span>
	 * <span class="zh-CN">生成的查询分析器实例对象</span>
	 * @throws SQLException <span class="en-US">if a database access error occurs or this method is called on a closed connection</span>
	 *                      <span class="zh-CN">如果发生数据库访问错误或在关闭的连接上调用此方法</span>
	 */
	private <T> T createStatement(final KeyType keyType, final String sql,
	                              final int resultSetType, final int resultSetConcurrency,
	                              final int resultSetHoldability, final int autoGeneratedKeys,
	                              final int[] columnIndexes, final String[] columnNames, final Class<T> statementType)
			throws SQLException {
		String cacheKey = this.cacheKey(keyType, sql, resultSetType, resultSetConcurrency,
				resultSetHoldability, autoGeneratedKeys, columnIndexes, columnNames);
		CachedStatement cachedStatement = null;
		if (this.cachedLimitSize > 0) {
			cachedStatement = this.cachedStatements.stream()
					.filter(existStatement -> ObjectUtils.nullSafeEquals(cacheKey, existStatement.getIdentifyKey()))
					.findFirst()
					.orElse(null);
		}
		if (cachedStatement == null) {
			cachedStatement = switch (keyType) {
				case CALL_ONLY -> new CachedCallableStatement(this, cacheKey,
						this.connection.prepareCall(sql));
				case CALL_CONCURRENCY -> new CachedCallableStatement(this, cacheKey,
						this.connection.prepareCall(sql, resultSetType, resultSetConcurrency));
				case CALL_HOLDABILITY -> new CachedCallableStatement(this, cacheKey,
						this.connection.prepareCall(sql, resultSetType,
								resultSetConcurrency, resultSetHoldability));
				case SQL_ONLY -> new CachedPreparedStatement(this, cacheKey,
						this.connection.prepareStatement(sql));
				case SQL_CONCURRENCY -> new CachedPreparedStatement(this, cacheKey,
						this.connection.prepareStatement(sql, resultSetType, resultSetConcurrency));
				case SQL_HOLDABILITY -> new CachedPreparedStatement(this, cacheKey,
						this.connection.prepareStatement(sql, resultSetType,
								resultSetConcurrency, resultSetHoldability));
				case SQL_AUTO_GENERATED_KEYS -> new CachedPreparedStatement(this, cacheKey,
						this.connection.prepareStatement(sql, autoGeneratedKeys));
				case SQL_COLUMN_INDEXES -> new CachedPreparedStatement(this, cacheKey,
						this.connection.prepareStatement(sql, columnIndexes));
				case SQL_COLUMN_NAMES -> new CachedPreparedStatement(this, cacheKey,
						this.connection.prepareStatement(sql, columnNames));
			};
			if (this.cachedLimitSize > 0) {
				this.cachedStatements.add(cachedStatement);
			}
		}
		if (this.cachedLimitSize > 0) {
			cachedStatement.incrementHitCount();
			this.cachedStatements.sort((o1, o2) -> Integer.compare(o2.getHitCount(), o1.getHitCount()));
			while (this.cachedLimitSize < this.cachedStatements.size()) {
				this.cachedStatements.remove(this.cachedStatements.size() - 1);
			}
		}
		this.activeConnection();
		return statementType.cast(cachedStatement);
	}

	/**
	 * <h4 class="en-US">Generate identification code of statement instance</h4>
	 * <h4 class="zh-CN">生成查询分析器缓存唯一识别代码</h4>
	 *
	 * @param keyType              <span class="en-US">Enumeration value of SQL command type</span>
	 *                             <span class="zh-CN">SQL语句类型枚举值</span>
	 * @param sql                  <span class="en-US">SQL command</span>
	 *                             <span class="zh-CN">SQL语句</span>
	 * @param resultSetType        <span class="en-US">Result set type code</span>
	 *                             <span class="zh-CN">结果集类型代码</span>
	 * @param resultSetConcurrency <span class="en-US">Result set data type code</span>
	 *                             <span class="zh-CN">结果集数据类型代码</span>
	 * @param resultSetHoldability <span class="en-US">Result set transactional type code</span>
	 *                             <span class="zh-CN">结果集事务类型代码</span>
	 * @param autoGeneratedKeys    <span class="en-US">Returns the flag for automatically generated key values</span>
	 *                             <span class="zh-CN">返回自动生成键值的标志</span>
	 * @param columnIndexes        <span class="en-US">Array of column indices that can be used to get the inserted row</span>
	 *                             <span class="zh-CN">可用于获取的插入行中的列索引数组</span>
	 * @param columnNames          <span class="en-US">Can be used to get an array of column names in the inserted row</span>
	 *                             <span class="zh-CN">可用于获取的插入行中的列名称数组</span>
	 * @return <span class="en-US">Generated identification code</span>
	 * <span class="zh-CN">生成的唯一识别代码</span>
	 */
	private String cacheKey(KeyType keyType, String sql, int resultSetType,
	                        int resultSetConcurrency, int resultSetHoldability, int autoGeneratedKeys,
	                        int[] columnIndexes, String[] columnNames) {
		TreeMap<String, Object> cacheMap = new TreeMap<>();
		cacheMap.put("KeyType", keyType.toString().toUpperCase());
		cacheMap.put("SQLCmd", sql);
		switch (keyType) {
			case SQL_CONCURRENCY:
			case CALL_CONCURRENCY:
				cacheMap.put("ResultSetType", resultSetType);
				cacheMap.put("ResultSetConcurrency", resultSetConcurrency);
				break;
			case SQL_HOLDABILITY:
			case CALL_HOLDABILITY:
				cacheMap.put("ResultSetType", resultSetType);
				cacheMap.put("ResultSetConcurrency", resultSetConcurrency);
				cacheMap.put("ResultSetHoldability", resultSetHoldability);
				break;
			case SQL_AUTO_GENERATED_KEYS:
				cacheMap.put("AutoGeneratedKeys", autoGeneratedKeys);
				break;
			case SQL_COLUMN_INDEXES:
				cacheMap.put("ColumnIndexes", columnIndexes);
				break;
			case SQL_COLUMN_NAMES:
				cacheMap.put("ColumnNames", columnNames);
				break;
		}
		String jsonData = StringUtils.objectToString(cacheMap, StringUtils.StringType.JSON, Boolean.FALSE);
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Cache_Statement_Map", jsonData);
		}
		return ConvertUtils.toHex(SecurityUtils.SHA256(jsonData));
	}
}
