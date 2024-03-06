
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

package org.nervousync.database.neurons;

import jakarta.annotation.Nonnull;
import org.nervousync.annotations.jmx.Monitor;
import org.nervousync.commons.Globals;
import org.nervousync.database.neurons.connection.NeuronsConnection;
import org.nervousync.jmx.AbstractMBean;
import org.nervousync.utils.ClassUtils;
import org.nervousync.utils.DateTimeUtils;
import org.nervousync.utils.LoggerUtils;
import org.nervousync.utils.StringUtils;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * <h2 class="en-US">JDBC data source implement class</h2>
 * <h2 class="zh-CN">JDBC数据源实现类</h2>
 *
 * @author Steven Wee	<a href="mailto:wmkm0113@Hotmail.com">wmkm0113@Hotmail.com</a>
 * @version $Revision: 1.0.0 $ $Date: Nov 12, 2020 12:20:49 $
 */
@Monitor(identify = "NeuronsDataSource", type = "DataSource", name = "Neurons")
public final class NeuronsDataSource extends AbstractMBean implements DataSource, Closeable, NeuronsDataSourceMBean {

	/**
	 * <span class="en-US">Multilingual logger instance object</span>
	 * <span class="zh-CN">多语言日志实例对象</span>
	 */
	private static final LoggerUtils.Logger LOGGER = LoggerUtils.getLogger(NeuronsDataSource.class);

	/**
	 * <span class="en-US">The interval between scheduled task executions</span>
	 * <span class="zh-CN">调度任务执行的间隔时间</span>
	 */
	private static final long SCHEDULE_PERIOD_TIME = 1000L;

	/**
	 * <span class="en-US">Database connection queue</span>
	 * <span class="zh-CN">数据库连接队列</span>
	 */
	private final Queue<NeuronsConnection> connectionPools;
	/**
	 * <span class="en-US">Using database connection list</span>
	 * <span class="zh-CN">使用中的数据库连接列表</span>
	 */
	private final List<NeuronsConnection> activeConnections;
	/**
	 * <span class="en-US">Waiting to get count of connections</span>
	 * <span class="zh-CN">等待获取连接的计数</span>
	 */
	private final AtomicInteger waitCount;
	private PrintWriter logWriter = new PrintWriter(System.out);

	/**
	 * <span class="en-US">Minimum connection limit</span>
	 * <span class="zh-CN">最小连接数</span>
	 */
	private int minConnections;
	/**
	 * <span class="en-US">Maximum connection limit</span>
	 * <span class="zh-CN">最大连接数</span>
	 */
	private int maxConnections;
	/**
	 * <span class="en-US">Timeout value of connection validate</span>
	 * <span class="zh-CN">连接检查超时时间</span>
	 */
	private int validateTimeout;
	/**
	 * <span class="en-US">Timeout value of create connection</span>
	 * <span class="zh-CN">建立连接超时时间</span>
	 */
	private int connectTimeout;
	/**
	 * <span class="en-US">Minimum connection limit</span>
	 * <span class="zh-CN">最小连接数</span>
	 */
	private int cacheLimitSize;
	/**
	 * <span class="en-US">Database username</span>
	 * <span class="zh-CN">数据库用户名</span>
	 */
	private String username;
	/**
	 * <span class="en-US">Database password</span>
	 * <span class="zh-CN">数据库密码</span>
	 */
	private String password;
	/**
	 * <span class="en-US">Database support transactional status</span>
	 * <span class="zh-CN">数据库支持事务状态值</span>
	 */
	private final boolean transactional;
	/**
	 * <span class="en-US">Check connection validate when obtains database connection</span>
	 * <span class="zh-CN">在获取连接时检查连接是否有效</span>
	 */
	private final boolean testOnBorrow;
	/**
	 * <span class="en-US">Check connection validate when return database connection</span>
	 * <span class="zh-CN">在归还连接时检查连接是否有效</span>
	 */
	private final boolean testOnReturn;
	/**
	 * <span class="en-US">JDBC connection url string</span>
	 * <span class="zh-CN">JDBC连接字符串</span>
	 */
	private final String jdbcUrl;
	/**
	 * <span class="en-US">JDBC properties information</span>
	 * <span class="zh-CN">JDBC配置信息</span>
	 */
	private final Properties jdbcProperties;
	/**
	 * <span class="en-US">Close status of data source</span>
	 * <span class="zh-CN">数据源关闭状态</span>
	 */
	private boolean closed = Boolean.FALSE;
	/**
	 * <span class="en-US">Thread instance object for create database connections</span>
	 * <span class="zh-CN">创建数据库连接的线程实例对象</span>
	 */
	private final CreateConnectionThread createConnectionThread;
	/**
	 * <span class="en-US">System scheduling task execution service</span>
	 * <span class="zh-CN">系统调度任务执行服务</span>
	 */
	private final ScheduledExecutorService executorService;

	/**
	 * <h4 class="en-US">Constructor method for JDBC data source implement class</h4>
	 * <h4 class="zh-CN">JDBC数据源实现类的构造方法</h4>
	 *
	 * @param minConnections  <span class="en-US">Minimum connection limit</span>
	 *                        <span class="zh-CN">最小连接数</span>
	 * @param maxConnections  <span class="en-US">Maximum connection limit</span>
	 *                        <span class="zh-CN">最大连接数</span>
	 * @param validateTimeout <span class="en-US">Timeout value of connection validate</span>
	 *                        <span class="zh-CN">连接检查超时时间</span>
	 * @param connectTimeout  <span class="en-US">Timeout value of create connection</span>
	 *                        <span class="zh-CN">建立连接超时时间</span>
	 * @param testOnBorrow    <span class="en-US">Check connection validate when obtains database connection</span>
	 *                        <span class="zh-CN">在获取连接时检查连接是否有效</span>
	 * @param testOnReturn    <span class="en-US">Check connection validate when return database connection</span>
	 *                        <span class="zh-CN">在归还连接时检查连接是否有效</span>
	 * @param retryLimit      <span class="en-US">Retry count if obtains connection has error</span>
	 *                        <span class="zh-CN">获取连接的重试次数</span>
	 * @param jdbcUrl         <span class="en-US">JDBC connection url string</span>
	 *                        <span class="zh-CN">JDBC连接字符串</span>
	 * @param jdbcProperties  <span class="en-US">JDBC properties information</span>
	 *                        <span class="zh-CN">JDBC配置信息</span>
	 * @param username        <span class="en-US">Database username</span>
	 *                        <span class="zh-CN">数据库用户名</span>
	 * @param password        <span class="en-US">Database password</span>
	 *                        <span class="zh-CN">数据库密码</span>
	 */
	public NeuronsDataSource(final int minConnections, final int maxConnections, final int validateTimeout,
	                         final int connectTimeout, final int cacheLimitSize, final boolean transactional,
	                         final boolean testOnBorrow, final boolean testOnReturn,
	                         final int retryLimit, final String jdbcUrl, final Properties jdbcProperties,
	                         final String username, final String password) {
		this.connectionPools = new LinkedList<>();
		this.activeConnections = new ArrayList<>();
		this.waitCount = new AtomicInteger(Globals.INITIALIZE_INT_VALUE);
		this.minConnections = minConnections;
		this.maxConnections = maxConnections;
		this.validateTimeout = validateTimeout;
		this.connectTimeout = connectTimeout;
		this.cacheLimitSize = cacheLimitSize;
		this.username = username;
		this.password = password;
		this.transactional = transactional;
		this.testOnBorrow = testOnBorrow;
		this.testOnReturn = testOnReturn;
		this.jdbcUrl = jdbcUrl;
		this.jdbcProperties = jdbcProperties;
		this.createConnectionThread = new CreateConnectionThread(this, retryLimit);
		this.createConnectionThread.start();
		this.executorService = Executors.newSingleThreadScheduledExecutor();
		this.executorService.scheduleWithFixedDelay(() -> {
					if (!this.createConnectionThread.isAlive()) {
						this.createConnectionThread.start();
					}
				},
				SCHEDULE_PERIOD_TIME, SCHEDULE_PERIOD_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {
		if (this.closed) {
			throw new SQLException();
		}

		long beginTime = DateTimeUtils.currentUTCTimeMillis();
		long timeOutTime = this.connectTimeout * 1000L;

		boolean waitCount = Boolean.FALSE;
		NeuronsConnection connection = null;

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Connection_Wait_Count", this.waitCount.get());
		}
		while (connection == null) {
			connection = this.connectionPools.poll();
			if (connection == null) {
				connection = this.openConnection();
			}

			if (this.testOnBorrow && connection != null && !this.checkConnection(connection)) {
				connection = null;
			}

			if (connection == null) {
				if (!waitCount) {
					this.waitCount.incrementAndGet();
					waitCount = Boolean.TRUE;
				}

				if (timeOutTime < (DateTimeUtils.currentUTCTimeMillis() - beginTime)) {
					break;
				}
			}
		}

		if (waitCount) {
			this.waitCount.decrementAndGet();
		}

		if (connection == null) {
			throw new SQLException("Obtain database connection error! ");
		}

		connection.activeConnection();
		this.activeConnections.add(connection);

		if (LOGGER.isDebugEnabled()) {
			if (waitCount) {
				LOGGER.debug("Connection_From_Create");
			} else {
				LOGGER.debug("Connection_From_Pool");
			}
			LOGGER.debug("Connection_Used_Time",
					DateTimeUtils.currentUTCTimeMillis() - beginTime);
			LOGGER.info("Check_Connection_Debug",
					DateTimeUtils.formatDateTime(new Date(connection.getConnectedTime())),
					DateTimeUtils.formatDateTime(new Date(connection.getLastActiveTime())));
			LOGGER.debug("Pool_Connection_Debug", this.getActiveCount(), this.getPoolCount());
		}
		return connection;
	}

	@Override
	public Connection getConnection(final String username, final String password) throws SQLException {
		this.username = username;
		this.password = password;
		return this.getConnection();
	}

	@Override
	public PrintWriter getLogWriter() {
		return this.logWriter;
	}

	@Override
	public void setLogWriter(final PrintWriter logWriter) {
		this.logWriter = logWriter;
	}

	@Override
	public void setLoginTimeout(int seconds) {
		DriverManager.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() {
		return DriverManager.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public <T> T unwrap(final Class<T> clazz) throws SQLException {
		try {
			return clazz.cast(this);
		} catch (ClassCastException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public boolean isWrapperFor(final Class<?> clazz) {
		return ClassUtils.isAssignable(clazz, this.getClass());
	}

	@Override
	public void close() {
		this.closed = Boolean.TRUE;
		this.executorService.shutdown();
		this.createConnectionThread.interrupt();
		Iterator<NeuronsConnection> iterator = this.activeConnections.iterator();
		while (iterator.hasNext()) {
			NeuronsConnection connection = iterator.next();
			this.destroyConnection(connection);
			iterator.remove();
		}
		Connection connection;
		while ((connection = this.connectionPools.poll()) != null) {
			try {
				connection.close();
			} catch (SQLException ignore) {
			}
		}
		this.connectionPools.clear();
	}

	@Override
	public int getPoolCount() {
		return this.connectionPools.size();
	}

	@Override
	public int getActiveCount() {
		return this.activeConnections.size();
	}

	@Override
	public int getWaitCount() {
		return this.waitCount.get();
	}

	@Override
	public boolean isClosed() {
		return this.closed;
	}

	@Override
	public void minConnections(int minConnections) {
		this.minConnections = minConnections;
	}

	@Override
	public void maxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	@Override
	public void validateTimeout(int timeout) {
		this.validateTimeout = timeout;
	}

	@Override
	public void connectTimeout(int timeout) {
		this.connectTimeout = timeout;
	}

	@Override
	public void cacheLimitSize(int cacheLimitSize) {
		this.cacheLimitSize = cacheLimitSize;
	}

	@Override
	public void retryLimit(final int retryLimit) {
		this.createConnectionThread.setRetryLimit(retryLimit);
	}

	@Override
	public void username(String username) {
		this.username = username;
	}

	@Override
	public void password(String password) {
		this.password = password;
	}

	public void closeConnection(final NeuronsConnection connection) throws SQLException {
		if (connection == null) {
			return;
		}

		if (this.closed) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Data_Source_Closed_Destroy_Connection");
			}
			this.destroyConnection(connection);
			return;
		}

		this.activeConnections.remove(connection);
		if (connection.isClosed()) {
			return;
		}

		if (this.testOnReturn && !this.checkConnection(connection)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Invalid_Destroy_Connection");
			}
			this.destroyConnection(connection);
			return;
		}

		this.addConnection(connection);
	}

	/**
	 * <h4 class="en-US">Check current connections count in connection pool is less than the pool maximum connections</h4>
	 * <h4 class="zh-CN">检查当前连接池中的连接数是否小于连接池最大连接数</h4>
	 *
	 * @return <span class="en-US">Check result</span>
	 * <span class="zh-CN">检查结果</span>
	 */
	private boolean needConnections() {
		return !this.limitConnections() && (this.connectionPools.size() < this.minConnections);
	}

	/**
	 * <h4 class="en-US">Check current connections count is greater or equal the maximum connections</h4>
	 * <h4 class="zh-CN">检查当前连接数是否超过最大连接数</h4>
	 *
	 * @return <span class="en-US">Check result</span>
	 * <span class="zh-CN">检查结果</span>
	 */
	private boolean limitConnections() {
		return this.maxConnections < (this.getActiveCount() + this.getPoolCount());
	}

	/**
	 * <h4 class="en-US">Create database connection</h4>
	 * <h4 class="zh-CN">建立数据库连接</h4>
	 *
	 * @return <span class="en-US">Database connection instance object</span>
	 * <span class="zh-CN">数据库连接实例对象</span>
	 */
	private NeuronsConnection openConnection() {
		if (this.limitConnections()) {
			return null;
		}
		Properties jdbcProperties = new Properties();
		jdbcProperties.putAll(this.jdbcProperties);
		if (StringUtils.notBlank(this.username)) {
			jdbcProperties.put("user", this.username);
			if (StringUtils.notBlank(this.password)) {
				jdbcProperties.put("password", this.password);
			}
		}
		try {
			NeuronsConnection connection =
					new NeuronsConnection(this, DriverManager.getConnection(this.jdbcUrl, jdbcProperties),
							this.transactional, this.cacheLimitSize);
			if (this.testOnBorrow && !this.checkConnection(connection)) {
				connection = null;
			}
			return connection;
		} catch (SQLException e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.error("Create_Connection_Error");
				LOGGER.debug("Stack_Message_Error", e);
			}
			return null;
		}
	}

	private boolean checkConnection(@Nonnull final NeuronsConnection connection) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Check_Connection_Debug",
					DateTimeUtils.formatDateTime(new Date(connection.getConnectedTime())),
					DateTimeUtils.formatDateTime(new Date(connection.getLastActiveTime())));
		}
		boolean validate;
		try {
			validate = connection.isValid(this.validateTimeout);
		} catch (SQLException e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Check_Connection_Error", e);
			}
			validate = Boolean.FALSE;
		}

		if (!validate) {
			this.destroyConnection(connection);
		}
		return validate;
	}

	private void destroyConnection(final NeuronsConnection connection) {
		try {
			if (connection == null || connection.isClosed()) {
				return;
			}
			connection.closeConnection();
		} catch (SQLException e) {
			LOGGER.error("Close_Connection_Error");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Stack_Message_Error", e);
			}
		}
	}

	private void addConnection(@Nonnull final NeuronsConnection connection) throws SQLException {
		boolean destroy = Boolean.TRUE;
		synchronized (this.connectionPools) {
			if ((this.connectionPools.size() < this.minConnections)) {
				connection.reset();
				destroy = !this.connectionPools.offer(connection);
			}
		}
		if (destroy) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pool_Full_Destroy_Connection");
			}
			this.destroyConnection(connection);
		}
	}

	private void checkConnections() {
		synchronized (this.connectionPools) {
			int poolSize = this.connectionPools.size(), count = Globals.INITIALIZE_INT_VALUE;
			while (count < poolSize) {
				NeuronsConnection connection = this.connectionPools.poll();
				if (connection == null) {
					break;
				}
				if (this.checkConnection(connection)) {
					this.connectionPools.offer(connection);
				} else {
					this.destroyConnection(connection);
				}
				count++;
			}
		}
	}

	/**
	 * <h2 class="en-US">Task thread to establish database connection</h2>
	 * <h2 class="zh-CN">建立数据库连接的任务线程</h2>
	 *
	 * @author Steven Wee	<a href="mailto:wmkm0113@Hotmail.com">wmkm0113@Hotmail.com</a>
	 * @version $Revision: 1.0.0 $ $Date: Nov 12, 2020 12:48:49 $
	 */
	private static final class CreateConnectionThread extends Thread {

		/**
		 * <span class="en-US">Data source instance object</span>
		 * <span class="zh-CN">数据源实例对象</span>
		 */
		private final NeuronsDataSource dataSource;
		/**
		 * <span class="en-US">Retry count if obtains connection has error</span>
		 * <span class="zh-CN">获取连接的重试次数</span>
		 */
		private int retryLimit;
		/**
		 * <span class="en-US">Task stopped status</span>
		 * <span class="zh-CN">任务停止运行状态</span>
		 */
		private boolean stopped = Boolean.FALSE;

		/**
		 * <h4 class="en-US">Constructor method for task thread to establish database connection</h4>
		 * <h4 class="zh-CN">建立数据库连接的任务线程的构造方法</h4>
		 *
		 * @param retryLimit <span class="en-US">Retry count if obtains connection has error</span>
		 *                   <span class="zh-CN">获取连接的重试次数</span>
		 * @param dataSource <span class="en-US">Data source instance object</span>
		 *                   <span class="zh-CN">数据源实例对象</span>
		 */
		CreateConnectionThread(final NeuronsDataSource dataSource, final int retryLimit) {
			this.setDaemon(Boolean.TRUE);
			this.dataSource = dataSource;
			this.retryLimit = retryLimit;
		}

		@Override
		public void run() {
			if (this.dataSource.limitConnections()) {
				return;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create_Connection_Begin_Debug");
			}
			this.dataSource.checkConnections();
			int retryCount = Globals.INITIALIZE_INT_VALUE;
			boolean process = Boolean.TRUE;
			while (process) {
				if (this.stopped) {
					break;
				}
				if (this.dataSource.needConnections()) {
					boolean waitConnect = Boolean.FALSE;
					NeuronsConnection connection = this.dataSource.openConnection();
					if (connection == null) {
						waitConnect = Boolean.TRUE;
					} else {
						try {
							this.dataSource.addConnection(connection);
						} catch (SQLException e) {
							LOGGER.error("Create_Connection_Error");
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("Stack_Message_Error", e);
							}
							waitConnect = Boolean.TRUE;
						}
					}
					if (waitConnect) {
						if (retryCount < this.retryLimit) {
							try {
								//  Wait 1 second and retry
								Thread.sleep(1000L);
							} catch (InterruptedException e) {
								LOGGER.error("Thread_Sleep_Error");
								if (LOGGER.isDebugEnabled()) {
									LOGGER.debug("Stack_Message_Error", e);
								}
								process = Boolean.FALSE;
							}
						} else {
							process = Boolean.FALSE;
						}
					}
				} else {
					process = Boolean.FALSE;
				}
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create_Connection_End_Debug");
			}
		}

		@Override
		public void interrupt() {
			super.interrupt();
			this.stopped = Boolean.TRUE;
		}

		/**
		 * <h4 class="en-US">Setter method for retry count if obtains connection has error</h4>
		 * <h4 class="zh-CN">获取连接的重试次数的Setter方法</h4>
		 *
		 * @param retryLimit <span class="en-US">Retry count if obtains connection has error</span>
		 *                   <span class="zh-CN">获取连接的重试次数</span>
		 */
		public void setRetryLimit(int retryLimit) {
			this.retryLimit = retryLimit;
		}
	}
}
