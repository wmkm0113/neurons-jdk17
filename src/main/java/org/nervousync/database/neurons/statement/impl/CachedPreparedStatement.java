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

package org.nervousync.database.neurons.statement.impl;

import org.nervousync.database.neurons.connection.NeuronsConnection;
import org.nervousync.database.neurons.statement.CachedStatement;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

/**
 * <h2 class="en-US">Cached prepared statement</h2>
 * <h2 class="zh-CN">可缓存的预编译查询执行器</h2>
 *
 * @author Steven Wee	<a href="mailto:wmkm0113@Hotmail.com">wmkm0113@Hotmail.com</a>
 * @version $Revision: 1.0.0 $ $Date: Oct 21, 2019 15:06:42 $
 */
public final class CachedPreparedStatement extends CachedStatement implements PreparedStatement {

	/**
	 * <h4 class="en-US">Constructor method for cached prepared statement</h4>
	 * <h4 class="zh-CN">可缓存的预编译查询执行器的构造方法</h4>
	 *
	 * @param connection  <span class="en-US">Database connection instance object</span>
	 *                    <span class="zh-CN">数据库连接实例对象</span>
	 * @param identifyKey <span class="en-US">Identification code</span>
	 *                    <span class="zh-CN">唯一识别代码</span>
	 * @param statement   <span class="en-US">JDBC prepared statement instance object</span>
	 *                    <span class="zh-CN">预编译查询执行器实例对象</span>
	 */
	public CachedPreparedStatement(final NeuronsConnection connection, final String identifyKey,
	                               final PreparedStatement statement) {
		super(connection, identifyKey, statement);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#close() 
	 */
	@Override
	public void close() throws SQLException {
		this.clearBatch();
		this.clearParameters();
		this.clearWarnings();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#executeQuery() 
	 */
	@Override
	public ResultSet executeQuery() throws SQLException {
		return this.unwrap(PreparedStatement.class).executeQuery();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#executeUpdate() 
	 */
	@Override
	public int executeUpdate() throws SQLException {
		return this.unwrap(PreparedStatement.class).executeUpdate();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setNull(int, int) 
	 */
	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		this.unwrap(PreparedStatement.class).setNull(parameterIndex, sqlType);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setBoolean(int, boolean) 
	 */
	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		this.unwrap(PreparedStatement.class).setBoolean(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setByte(int, byte) 
	 */
	@Override
	public void setByte(int parameterIndex, byte x) throws SQLException {
		this.unwrap(PreparedStatement.class).setByte(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setShort(int, short) 
	 */
	@Override
	public void setShort(int parameterIndex, short x) throws SQLException {
		this.unwrap(PreparedStatement.class).setShort(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setInt(int, int) 
	 */
	@Override
	public void setInt(int parameterIndex, int x) throws SQLException {
		this.unwrap(PreparedStatement.class).setInt(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setLong(int, long) 
	 */
	@Override
	public void setLong(int parameterIndex, long x) throws SQLException {
		this.unwrap(PreparedStatement.class).setLong(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setFloat(int, float) 
	 */
	@Override
	public void setFloat(int parameterIndex, float x) throws SQLException {
		this.unwrap(PreparedStatement.class).setFloat(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setDouble(int, double) 
	 */
	@Override
	public void setDouble(int parameterIndex, double x) throws SQLException {
		this.unwrap(PreparedStatement.class).setDouble(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setBigDecimal(int, BigDecimal) 
	 */
	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		this.unwrap(PreparedStatement.class).setBigDecimal(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setString(int, String) 
	 */
	@Override
	public void setString(int parameterIndex, String x) throws SQLException {
		this.unwrap(PreparedStatement.class).setString(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setBytes(int, byte[]) 
	 */
	@Override
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		this.unwrap(PreparedStatement.class).setBytes(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setDate(int, Date) 
	 */
	@Override
	public void setDate(int parameterIndex, Date x) throws SQLException {
		this.unwrap(PreparedStatement.class).setDate(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setTime(int, Time) 
	 */
	@Override
	public void setTime(int parameterIndex, Time x) throws SQLException {
		this.unwrap(PreparedStatement.class).setTime(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setTimestamp(int, Timestamp) 
	 */
	@Override
	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		this.unwrap(PreparedStatement.class).setTimestamp(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setAsciiStream(int, InputStream, int) 
	 */
	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		this.unwrap(PreparedStatement.class).setAsciiStream(parameterIndex, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setUnicodeStream(int, InputStream, int) 
	 */
	@Override
	@Deprecated
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
		this.unwrap(PreparedStatement.class).setUnicodeStream(parameterIndex, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setBinaryStream(int, InputStream, int) 
	 */
	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		this.unwrap(PreparedStatement.class).setBinaryStream(parameterIndex, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#clearParameters() 
	 */
	@Override
	public void clearParameters() throws SQLException {
		this.unwrap(PreparedStatement.class).clearParameters();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setObject(int, Object, int, int) 
	 */
	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		this.unwrap(PreparedStatement.class).setObject(parameterIndex, x, targetSqlType);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setObject(int, Object) 
	 */
	@Override
	public void setObject(int parameterIndex, Object x) throws SQLException {
		this.unwrap(PreparedStatement.class).setObject(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#execute() 
	 */
	@Override
	public boolean execute() throws SQLException {
		return this.unwrap(PreparedStatement.class).execute();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#addBatch() 
	 */
	@Override
	public void addBatch() throws SQLException {
		this.unwrap(PreparedStatement.class).addBatch();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setCharacterStream(int, Reader, int) 
	 */
	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		this.unwrap(PreparedStatement.class).setCharacterStream(parameterIndex, reader, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setRef(int, Ref) 
	 */
	@Override
	public void setRef(int parameterIndex, Ref x) throws SQLException {
		this.unwrap(PreparedStatement.class).setRef(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setBlob(int, Blob) 
	 */
	@Override
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		this.unwrap(PreparedStatement.class).setBlob(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setClob(int, Clob) 
	 */
	@Override
	public void setClob(int parameterIndex, Clob x) throws SQLException {
		this.unwrap(PreparedStatement.class).setClob(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setArray(int, Array) 
	 */
	@Override
	public void setArray(int parameterIndex, Array x) throws SQLException {
		this.unwrap(PreparedStatement.class).setArray(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#getMetaData() 
	 */
	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return this.unwrap(PreparedStatement.class).getMetaData();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setDate(int, Date, Calendar) 
	 */
	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		this.unwrap(PreparedStatement.class).setDate(parameterIndex, x, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setTime(int, Time, Calendar) 
	 */
	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		this.unwrap(PreparedStatement.class).setTime(parameterIndex, x, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setTimestamp(int, Timestamp, Calendar) 
	 */
	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		this.unwrap(PreparedStatement.class).setTimestamp(parameterIndex, x, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setNull(int, int, String) 
	 */
	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		this.unwrap(PreparedStatement.class).setNull(parameterIndex, sqlType, typeName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setURL(int, URL) 
	 */
	@Override
	public void setURL(int parameterIndex, URL x) throws SQLException {
		this.unwrap(PreparedStatement.class).setURL(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#getParameterMetaData() 
	 */
	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		return this.unwrap(PreparedStatement.class).getParameterMetaData();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setRowId(int, RowId) 
	 */
	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		this.unwrap(PreparedStatement.class).setRowId(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setNString(int, String) 
	 */
	@Override
	public void setNString(int parameterIndex, String value) throws SQLException {
		this.unwrap(PreparedStatement.class).setNString(parameterIndex, value);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setNCharacterStream(int, Reader, long) 
	 */
	@Override
	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
		this.unwrap(PreparedStatement.class).setNCharacterStream(parameterIndex, value, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setNClob(int, NClob) 
	 */
	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		this.unwrap(PreparedStatement.class).setNClob(parameterIndex, value);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setClob(int, Reader, long) 
	 */
	@Override
	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
		this.unwrap(PreparedStatement.class).setClob(parameterIndex, reader, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setBlob(int, InputStream, long) 
	 */
	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
		this.unwrap(PreparedStatement.class).setBlob(parameterIndex, inputStream, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setNClob(int, Reader, long) 
	 */
	@Override
	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
		this.unwrap(PreparedStatement.class).setNClob(parameterIndex, reader, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setSQLXML(int, SQLXML) 
	 */
	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
		this.unwrap(PreparedStatement.class).setSQLXML(parameterIndex, xmlObject);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setObject(int, Object, int, int) 
	 */
	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
		this.unwrap(PreparedStatement.class).setObject(parameterIndex, x, targetSqlType, scaleOrLength);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setAsciiStream(int, InputStream, long) 
	 */
	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
		this.unwrap(PreparedStatement.class).setAsciiStream(parameterIndex, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setBinaryStream(int, InputStream, long) 
	 */
	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
		this.unwrap(PreparedStatement.class).setBinaryStream(parameterIndex, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setCharacterStream(int, Reader, long) 
	 */
	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
		this.unwrap(PreparedStatement.class).setCharacterStream(parameterIndex, reader, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setAsciiStream(int, InputStream) 
	 */
	@Override
	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		this.unwrap(PreparedStatement.class).setAsciiStream(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setBinaryStream(int, InputStream) 
	 */
	@Override
	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		this.unwrap(PreparedStatement.class).setBinaryStream(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setCharacterStream(int, Reader) 
	 */
	@Override
	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		this.unwrap(PreparedStatement.class).setCharacterStream(parameterIndex, reader);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setNCharacterStream(int, Reader) 
	 */
	@Override
	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
		this.unwrap(PreparedStatement.class).setNCharacterStream(parameterIndex, value);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setClob(int, Reader) 
	 */
	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		this.unwrap(PreparedStatement.class).setClob(parameterIndex, reader);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setBlob(int, InputStream) 
	 */
	@Override
	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
		this.unwrap(PreparedStatement.class).setBlob(parameterIndex, inputStream);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see PreparedStatement#setNClob(int, Reader) 
	 */
	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		this.unwrap(PreparedStatement.class).setNClob(parameterIndex, reader);
	}
}
