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

package org.nervousync.database.neurons.statement;

import org.nervousync.commons.Globals;
import org.nervousync.database.neurons.connection.NeuronsConnection;

import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h2 class="en-US">Abstract class for cached statement</h2>
 * <h2 class="zh-CN">可缓存的查询执行器抽象类</h2>
 *
 * @author Steven Wee	<a href="mailto:wmkm0113@Hotmail.com">wmkm0113@Hotmail.com</a>
 * @version $Revision: 1.0.0 $ $Date: Oct 21, 2019 14:27:33 $
 */
public abstract class CachedStatement implements Statement {

	/**
	 * <span class="en-US">Database connection instance object</span>
	 * <span class="zh-CN">数据库连接实例对象</span>
	 */
	private final NeuronsConnection connection;
	/**
	 * <span class="en-US">JDBC statement instance object</span>
	 * <span class="zh-CN">查询执行器实例对象</span>
	 */
	private final Statement statement;
	/**
	 * <span class="en-US">Hit count</span>
	 * <span class="zh-CN">命中的次数</span>
	 */
	private final AtomicInteger hitCount;
	/**
	 * <span class="en-US">Identification code</span>
	 * <span class="zh-CN">唯一识别代码</span>
	 */
	private final String identifyKey;

	/**
	 * <h4 class="en-US">Constructor method for abstract class for cached statement</h4>
	 * <h4 class="zh-CN">可缓存的查询执行器抽象类的构造方法</h4>
	 *
	 * @param connection  <span class="en-US">Database connection instance object</span>
	 *                    <span class="zh-CN">数据库连接实例对象</span>
	 * @param identifyKey <span class="en-US">Identification code</span>
	 *                    <span class="zh-CN">唯一识别代码</span>
	 * @param statement   <span class="en-US">JDBC statement instance object</span>
	 *                    <span class="zh-CN">查询执行器实例对象</span>
	 */
	protected CachedStatement(final NeuronsConnection connection, final String identifyKey, final Statement statement) {
		this.connection = connection;
		this.statement = statement;
		this.hitCount = new AtomicInteger(Globals.INITIALIZE_INT_VALUE);
		this.identifyKey = identifyKey;
	}

	/**
	 * <h4 class="en-US">Increment hit count</h4>
	 * <h4 class="zh-CN">增加命中次数</h4>
	 */
	public void incrementHitCount() {
		this.hitCount.incrementAndGet();
	}

	/**
	 * <h4 class="en-US">Getter method for hit count</h4>
	 * <h4 class="zh-CN">命中的次数的Getter方法</h4>
	 *
	 * @return <span class="en-US">Hit count</span>
	 * <span class="zh-CN">命中的次数</span>
	 */
	public int getHitCount() {
		return this.hitCount.get();
	}

	/**
	 * <h4 class="en-US">Getter method for identification code</h4>
	 * <h4 class="zh-CN">唯一识别代码的Getter方法</h4>
	 *
	 * @return <span class="en-US">Identification code</span>
	 * <span class="zh-CN">唯一识别代码</span>
	 */
	public String getIdentifyKey() {
		return this.identifyKey;
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#isWrapperFor(Class) 
	 */
	@Override
	public boolean isWrapperFor(Class<?> clazz) throws SQLException {
		if (clazz == null) {
			return Boolean.FALSE;
		}

		if (clazz == this.statement.getClass() || clazz == this.getClass()) {
			return true;
		}

		return this.statement.isWrapperFor(clazz);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#unwrap(Class) 
	 */
	@Override
	public <T> T unwrap(Class<T> clazz) throws SQLException {
		if (clazz == null) {
			return null;
		}

		if (clazz == this.statement.getClass()) {
			return clazz.cast(this.statement);
		}

		if (clazz == this.getClass()) {
			return clazz.cast(this);
		}

		return this.statement.unwrap(clazz);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#executeQuery(String) 
	 */
	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		return this.statement.executeQuery(sql);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#executeUpdate(String) 
	 */
	@Override
	public int executeUpdate(String sql) throws SQLException {
		return this.statement.executeUpdate(sql);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#close() 
	 */
	@Override
	public void close() throws SQLException {
		this.clearBatch();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getMaxFieldSize() 
	 */
	@Override
	public int getMaxFieldSize() throws SQLException {
		return this.statement.getMaxFieldSize();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#setMaxFieldSize(int) 
	 */
	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		this.statement.setMaxFieldSize(max);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getMaxRows() 
	 */
	@Override
	public int getMaxRows() throws SQLException {
		return this.statement.getMaxRows();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#setMaxRows(int) 
	 */
	@Override
	public void setMaxRows(int max) throws SQLException {
		this.statement.setMaxRows(max);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#setEscapeProcessing(boolean) 
	 */
	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		this.statement.setEscapeProcessing(enable);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getQueryTimeout() 
	 */
	@Override
	public int getQueryTimeout() throws SQLException {
		return this.statement.getQueryTimeout();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#setQueryTimeout(int) 
	 */
	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		this.statement.setQueryTimeout(seconds);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#cancel() 
	 */
	@Override
	public void cancel() throws SQLException {
		this.statement.cancel();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getWarnings() 
	 */
	@Override
	public SQLWarning getWarnings() throws SQLException {
		return this.statement.getWarnings();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#clearWarnings() 
	 */
	@Override
	public void clearWarnings() throws SQLException {
		this.statement.clearWarnings();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#setCursorName(String) 
	 */
	@Override
	public void setCursorName(String name) throws SQLException {
		this.statement.setCursorName(name);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#execute(String) 
	 */
	@Override
	public boolean execute(String sql) throws SQLException {
		return this.statement.execute(sql);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getResultSet() 
	 */
	@Override
	public ResultSet getResultSet() throws SQLException {
		return this.statement.getResultSet();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getUpdateCount() 
	 */
	@Override
	public int getUpdateCount() throws SQLException {
		return this.statement.getUpdateCount();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getMoreResults() 
	 */
	@Override
	public boolean getMoreResults() throws SQLException {
		return this.statement.getMoreResults();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#setFetchDirection(int) 
	 */
	@Override
	public void setFetchDirection(int direction) throws SQLException {
		this.statement.setFetchDirection(direction);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getFetchDirection()
	 */
	@Override
	public int getFetchDirection() throws SQLException {
		return this.statement.getFetchDirection();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#setFetchSize(int) 
	 */
	@Override
	public void setFetchSize(int rows) throws SQLException {
		this.statement.setFetchSize(rows);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getFetchSize() 
	 */
	@Override
	public int getFetchSize() throws SQLException {
		return this.statement.getFetchSize();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getResultSetConcurrency() 
	 */
	@Override
	public int getResultSetConcurrency() throws SQLException {
		return this.statement.getResultSetConcurrency();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getResultSetType() 
	 */
	@Override
	public int getResultSetType() throws SQLException {
		return this.statement.getResultSetType();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#addBatch(String) 
	 */
	@Override
	public void addBatch(String sql) throws SQLException {
		this.statement.addBatch(sql);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#clearBatch() 
	 */
	@Override
	public void clearBatch() throws SQLException {
		this.statement.clearBatch();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#executeBatch() 
	 */
	@Override
	public int[] executeBatch() throws SQLException {
		return this.statement.executeBatch();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getConnection() 
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return this.connection;
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getMoreResults(int) 
	 */
	@Override
	public boolean getMoreResults(int current) throws SQLException {
		return this.statement.getMoreResults(current);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getGeneratedKeys() 
	 */
	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		return this.statement.getGeneratedKeys();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#executeUpdate(String, int) 
	 */
	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		return this.statement.executeUpdate(sql, autoGeneratedKeys);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#executeUpdate(String, int[]) 
	 */
	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		return this.statement.executeUpdate(sql, columnIndexes);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#executeUpdate(String, String[]) 
	 */
	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		return this.statement.executeUpdate(sql, columnNames);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#execute(String, int) 
	 */
	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		return this.statement.execute(sql, autoGeneratedKeys);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#execute(String, int[]) 
	 */
	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		return this.statement.execute(sql, columnIndexes);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#execute(String, String[]) 
	 */
	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		return this.statement.execute(sql, columnNames);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getResultSetHoldability() 
	 */
	@Override
	public int getResultSetHoldability() throws SQLException {
		return this.statement.getResultSetHoldability();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#isClosed() 
	 */
	@Override
	public boolean isClosed() throws SQLException {
		return this.statement.isClosed();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#setPoolable(boolean) 
	 */
	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		this.statement.setPoolable(poolable);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#isPoolable() 
	 */
	@Override
	public boolean isPoolable() throws SQLException {
		return this.statement.isPoolable();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#closeOnCompletion() 
	 */
	@Override
	public void closeOnCompletion() throws SQLException {
		this.statement.closeOnCompletion();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#isCloseOnCompletion() 
	 */
	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		return this.statement.isCloseOnCompletion();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getLargeUpdateCount() 
	 */
	@Override
	public long getLargeUpdateCount() throws SQLException {
		return this.statement.getLargeUpdateCount();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#setLargeMaxRows(long) 
	 */
	@Override
	public void setLargeMaxRows(long max) throws SQLException {
		this.statement.setLargeMaxRows(max);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#getLargeMaxRows() 
	 */
	@Override
	public long getLargeMaxRows() throws SQLException {
		return this.statement.getLargeMaxRows();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#executeLargeBatch() 
	 */
	@Override
	public long[] executeLargeBatch() throws SQLException {
		return this.statement.executeLargeBatch();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#executeLargeUpdate(String) 
	 */
	@Override
	public long executeLargeUpdate(String sql) throws SQLException {
		return this.statement.executeLargeUpdate(sql);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#executeLargeUpdate(String, int) 
	 */
	@Override
	public long executeLargeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		return this.statement.executeLargeUpdate(sql, autoGeneratedKeys);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#executeLargeUpdate(String, int[]) 
	 */
	@Override
	public long executeLargeUpdate(String sql, int[] columnIndexes) throws SQLException {
		return this.statement.executeLargeUpdate(sql, columnIndexes);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see Statement#executeLargeUpdate(String, String[])
	 */
	@Override
	public long executeLargeUpdate(String sql, String[] columnNames) throws SQLException {
		return this.statement.executeLargeUpdate(sql, columnNames);
	}
}
