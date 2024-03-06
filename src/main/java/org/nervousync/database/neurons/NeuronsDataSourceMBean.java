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

public interface NeuronsDataSourceMBean {

	/**
	 * <h4 class="en-US">Connections count in connection pool</h4>
	 * <h4 class="zh-CN">连接池中现有连接数</h4>
	 *
	 * @return <span class="en-US">Connections count</span>
	 * <span class="zh-CN">连接数</span>
	 */
	int getPoolCount();

	/**
	 * <h4 class="en-US">Activated connections count</h4>
	 * <h4 class="zh-CN">使用中的连接数</h4>
	 *
	 * @return <span class="en-US">Connections count</span>
	 * <span class="zh-CN">连接数</span>
	 */
	int getActiveCount();

	/**
	 * <h4 class="en-US">Waiting to get count of connections</h4>
	 * <h4 class="zh-CN">等待获取连接的计数</h4>
	 *
	 * @return <span class="en-US">Waiting to get count of connections</span>
	 * <span class="zh-CN">等待获取连接的计数</span>
	 */
	int getWaitCount();

	/**
	 * <h4 class="en-US">Data source was closed</h4>
	 * <h4 class="zh-CN">数据源已经关闭</h4>
	 *
	 * @return <span class="en-US">Close status of data source</span>
	 * <span class="zh-CN">数据源关闭状态</span>
	 */
	boolean isClosed();

	/**
	 * <h4 class="en-US">Configure minimum connection limit of database connection pool</h4>
	 * <h4 class="zh-CN">设置数据库连接池最小连接数</h4>
	 *
	 * @param minConnections <span class="en-US">Minimum connection limit</span>
	 *                       <span class="zh-CN">最小连接数</span>
	 */
	void minConnections(final int minConnections);

	/**
	 * <h4 class="en-US">Configure maximum connection limit of database connection pool</h4>
	 * <h4 class="zh-CN">设置数据库连接池最大连接数</h4>
	 *
	 * @param maxConnections <span class="en-US">Maximum connection limit</span>
	 *                       <span class="zh-CN">最大连接数</span>
	 */
	void maxConnections(final int maxConnections);

	/**
	 * <h4 class="en-US">Configure timeout value of database connection validate check</h4>
	 * <h4 class="zh-CN">设置连接检查超时时间</h4>
	 *
	 * @param timeout <span class="en-US">Timeout value</span>
	 *                <span class="zh-CN">超时时间</span>
	 */
	void validateTimeout(final int timeout);

	/**
	 * <h4 class="en-US">Configure timeout value of create database connection</h4>
	 * <h4 class="zh-CN">设置创建数据库连接的超时时间</h4>
	 *
	 * @param timeout <span class="en-US">Timeout value</span>
	 *                <span class="zh-CN">超时时间</span>
	 */
	void connectTimeout(final int timeout);

	/**
	 * <h4 class="en-US">Configure cached limit value of statement</h4>
	 * <h4 class="zh-CN">设置缓存的查询分析器最大值</h4>
	 *
	 * @param cacheLimitSize <span class="en-US">Maximum value</span>
	 *                       <span class="zh-CN">最大值</span>
	 */
	void cacheLimitSize(int cacheLimitSize);

	/**
	 * <h4 class="en-US">Configure retry limit value of create connection if failed</h4>
	 * <h4 class="zh-CN">设置获取数据库连接的最大重试次数</h4>
	 *
	 * @param retryLimit <span class="en-US">Maximum retry limit value</span>
	 *                   <span class="zh-CN">最大重试次数</span>
	 */
	void retryLimit(final int retryLimit);

	/**
	 * <h4 class="en-US">Configure username of database connection pool</h4>
	 * <h4 class="zh-CN">设置数据库连接用户名</h4>
	 *
	 * @param username <span class="en-US">Username</span>
	 *                 <span class="zh-CN">用户名</span>
	 */
	void username(final String username);

	/**
	 * <h4 class="en-US">Configure password of database connection pool</h4>
	 * <h4 class="zh-CN">设置数据库连接密码</h4>
	 *
	 * @param password <span class="en-US">Password</span>
	 *                 <span class="zh-CN">密码</span>
	 */
	void password(final String password);
}
