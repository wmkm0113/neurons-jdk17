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
import java.util.Map;

/**
 * <h2 class="en-US">Cached callable statement</h2>
 * <h2 class="zh-CN">可缓存的存储过程执行器</h2>
 *
 * @author Steven Wee	<a href="mailto:wmkm0113@Hotmail.com">wmkm0113@Hotmail.com</a>
 * @version $Revision: 1.0.0 $ $Date: Oct 21, 2019 15:18:06 $
 */
public final class CachedCallableStatement extends CachedStatement implements CallableStatement {

	/**
	 * <h4 class="en-US">Constructor method for cached callable statement</h4>
	 * <h4 class="zh-CN">可缓存的存储过程执行器的构造方法</h4>
	 *
	 * @param connection  <span class="en-US">Database connection instance object</span>
	 *                    <span class="zh-CN">数据库连接实例对象</span>
	 * @param identifyKey <span class="en-US">Identification code</span>
	 *                    <span class="zh-CN">唯一识别代码</span>
	 * @param statement   <span class="en-US">JDBC callable statement instance object</span>
	 *                    <span class="zh-CN">存储过程执行器实例对象</span>
	 */
	public CachedCallableStatement(final NeuronsConnection connection, final String identifyKey,
	                               final CallableStatement statement) {
		super(connection, identifyKey, statement);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#registerOutParameter(int, int) 
	 */
	@Override
	public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
		this.unwrap(CallableStatement.class).registerOutParameter(parameterIndex, sqlType);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#registerOutParameter(int, int, int) 
	 */
	@Override
	public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
		this.unwrap(CallableStatement.class).registerOutParameter(parameterIndex, sqlType, scale);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#wasNull() 
	 */
	@Override
	public boolean wasNull() throws SQLException {
		return this.unwrap(CallableStatement.class).wasNull();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getString(int) 
	 */
	@Override
	public String getString(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getString(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getBoolean(int) 
	 */
	@Override
	public boolean getBoolean(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getBoolean(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getByte(int) 
	 */
	@Override
	public byte getByte(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getByte(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getShort(int) 
	 */
	@Override
	public short getShort(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getShort(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getInt(int) 
	 */
	@Override
	public int getInt(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getInt(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getLong(int) 
	 */
	@Override
	public long getLong(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getLong(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getFloat(int) 
	 */
	@Override
	public float getFloat(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getFloat(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getDouble(int) 
	 */
	@Override
	public double getDouble(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getDouble(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getBigDecimal(int) 
	 */
	@Override
	@Deprecated
	public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
		return this.unwrap(CallableStatement.class).getBigDecimal(parameterIndex, scale);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getByte(int) 
	 */
	@Override
	public byte[] getBytes(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getBytes(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getDate(int) 
	 */
	@Override
	public Date getDate(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getDate(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getTime(int) 
	 */
	@Override
	public Time getTime(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getTime(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getTimestamp(int) 
	 */
	@Override
	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getTimestamp(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getObject(int) 
	 */
	@Override
	public Object getObject(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getObject(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getBigDecimal(int) 
	 */
	@Override
	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getBigDecimal(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getObject(int, Map) 
	 */
	@Override
	public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
		return this.unwrap(CallableStatement.class).getObject(parameterIndex, map);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getRef(int) 
	 */
	@Override
	public Ref getRef(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getRef(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getBlob(int) 
	 */
	@Override
	public Blob getBlob(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getBlob(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getClob(int) 
	 */
	@Override
	public Clob getClob(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getClob(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getArray(int) 
	 */
	@Override
	public Array getArray(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getArray(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getDate(int, Calendar) 
	 */
	@Override
	public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
		return this.unwrap(CallableStatement.class).getDate(parameterIndex, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getTime(int, Calendar) 
	 */
	@Override
	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		return this.unwrap(CallableStatement.class).getTime(parameterIndex, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getTimestamp(int, Calendar) 
	 */
	@Override
	public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
		return this.unwrap(CallableStatement.class).getTimestamp(parameterIndex, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#registerOutParameter(int, int, String) 
	 */
	@Override
	public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
		this.unwrap(CallableStatement.class).registerOutParameter(parameterIndex, sqlType, typeName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#registerOutParameter(String, int) 
	 */
	@Override
	public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
		this.unwrap(CallableStatement.class).registerOutParameter(parameterName, sqlType);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#registerOutParameter(String, int, int) 
	 */
	@Override
	public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
		this.unwrap(CallableStatement.class).registerOutParameter(parameterName, sqlType, scale);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#registerOutParameter(String, int, String) 
	 */
	@Override
	public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
		this.unwrap(CallableStatement.class).registerOutParameter(parameterName, sqlType, typeName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getURL(int) 
	 */
	@Override
	public URL getURL(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getURL(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setURL(int, URL) 
	 */
	@Override
	public void setURL(String parameterName, URL val) throws SQLException {
		this.unwrap(CallableStatement.class).setURL(parameterName, val);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNull(String, int) 
	 */
	@Override
	public void setNull(String parameterName, int sqlType) throws SQLException {
		this.unwrap(CallableStatement.class).setNull(parameterName, sqlType);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBoolean(String, boolean) 
	 */
	@Override
	public void setBoolean(String parameterName, boolean x) throws SQLException {
		this.unwrap(CallableStatement.class).setBoolean(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setByte(String, byte) 
	 */
	@Override
	public void setByte(String parameterName, byte x) throws SQLException {
		this.unwrap(CallableStatement.class).setByte(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setShort(String, short) 
	 */
	@Override
	public void setShort(String parameterName, short x) throws SQLException {
		this.unwrap(CallableStatement.class).setShort(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setInt(String, int) 
	 */
	@Override
	public void setInt(String parameterName, int x) throws SQLException {
		this.unwrap(CallableStatement.class).setInt(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setLong(String, long) 
	 */
	@Override
	public void setLong(String parameterName, long x) throws SQLException {
		this.unwrap(CallableStatement.class).setLong(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setFloat(String, float) 
	 */
	@Override
	public void setFloat(String parameterName, float x) throws SQLException {
		this.unwrap(CallableStatement.class).setFloat(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setDouble(String, double) 
	 */
	@Override
	public void setDouble(String parameterName, double x) throws SQLException {
		this.unwrap(CallableStatement.class).setDouble(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBigDecimal(String, BigDecimal) 
	 */
	@Override
	public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
		this.unwrap(CallableStatement.class).setBigDecimal(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setString(String, String) 
	 */
	@Override
	public void setString(String parameterName, String x) throws SQLException {
		this.unwrap(CallableStatement.class).setString(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBytes(String, byte[]) 
	 */
	@Override
	public void setBytes(String parameterName, byte[] x) throws SQLException {
		this.unwrap(CallableStatement.class).setBytes(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setDate(String, Date) 
	 */
	@Override
	public void setDate(String parameterName, Date x) throws SQLException {
		this.unwrap(CallableStatement.class).setDate(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setTime(String, Time) 
	 */
	@Override
	public void setTime(String parameterName, Time x) throws SQLException {
		this.unwrap(CallableStatement.class).setTime(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setTimestamp(String, Timestamp) 
	 */
	@Override
	public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
		this.unwrap(CallableStatement.class).setTimestamp(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setAsciiStream(String, InputStream, int) 
	 */
	@Override
	public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
		this.unwrap(CallableStatement.class).setAsciiStream(parameterName, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBinaryStream(String, InputStream, int) 
	 */
	@Override
	public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
		this.unwrap(CallableStatement.class).setBinaryStream(parameterName, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setObject(String, Object, int, int) 
	 */
	@Override
	public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
		this.unwrap(CallableStatement.class).setObject(parameterName, x, targetSqlType, scale);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setObject(String, Object, int) 
	 */
	@Override
	public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
		this.unwrap(CallableStatement.class).setObject(parameterName, x, targetSqlType);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setObject(String, Object) 
	 */
	@Override
	public void setObject(String parameterName, Object x) throws SQLException {
		this.unwrap(CallableStatement.class).setObject(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setCharacterStream(String, Reader, int) 
	 */
	@Override
	public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
		this.unwrap(CallableStatement.class).setCharacterStream(parameterName, reader, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setDate(String, Date, Calendar) 
	 */
	@Override
	public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
		this.unwrap(CallableStatement.class).setDate(parameterName, x, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setTime(String, Time, Calendar) 
	 */
	@Override
	public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
		this.unwrap(CallableStatement.class).setTime(parameterName, x, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setTimestamp(String, Timestamp, Calendar) 
	 */
	@Override
	public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
		this.unwrap(CallableStatement.class).setTimestamp(parameterName, x, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNull(String, int, String) 
	 */
	@Override
	public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
		this.unwrap(CallableStatement.class).setNull(parameterName, sqlType, typeName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getString(String) 
	 */
	@Override
	public String getString(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getString(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getBoolean(String) 
	 */
	@Override
	public boolean getBoolean(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getBoolean(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getByte(String) 
	 */
	@Override
	public byte getByte(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getByte(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getShort(String) 
	 */
	@Override
	public short getShort(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getShort(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getInt(String) 
	 */
	@Override
	public int getInt(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getInt(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getLong(int) 
	 */
	@Override
	public long getLong(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getLong(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getFloat(String) 
	 */
	@Override
	public float getFloat(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getFloat(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getDouble(String) 
	 */
	@Override
	public double getDouble(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getDouble(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getBytes(String) 
	 */
	@Override
	public byte[] getBytes(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getBytes(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getDate(String) 
	 */
	@Override
	public Date getDate(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getDate(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getTime(String) 
	 */
	@Override
	public Time getTime(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getTime(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getTimestamp(String) 
	 */
	@Override
	public Timestamp getTimestamp(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getTimestamp(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getObject(String)  
	 */
	@Override
	public Object getObject(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getObject(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getBigDecimal(String) 
	 */
	@Override
	public BigDecimal getBigDecimal(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getBigDecimal(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getObject(String, Map) 
	 */
	@Override
	public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
		return this.unwrap(CallableStatement.class).getObject(parameterName, map);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getRef(String) 
	 */
	@Override
	public Ref getRef(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getRef(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getBlob(String) 
	 */
	@Override
	public Blob getBlob(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getBlob(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getClob(String) 
	 */
	@Override
	public Clob getClob(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getClob(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getArray(String) 
	 */
	@Override
	public Array getArray(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getArray(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getDate(String, Calendar) 
	 */
	@Override
	public Date getDate(String parameterName, Calendar cal) throws SQLException {
		return this.unwrap(CallableStatement.class).getDate(parameterName, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getTime(String, Calendar) 
	 */
	@Override
	public Time getTime(String parameterName, Calendar cal) throws SQLException {
		return this.unwrap(CallableStatement.class).getTime(parameterName, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getTimestamp(String, Calendar) 
	 */
	@Override
	public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
		return this.unwrap(CallableStatement.class).getTimestamp(parameterName, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getURL(String) 
	 */
	@Override
	public URL getURL(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getURL(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getRowId(int) 
	 */
	@Override
	public RowId getRowId(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getRowId(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getRowId(String) 
	 */
	@Override
	public RowId getRowId(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getRowId(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setRowId(String, RowId) 
	 */
	@Override
	public void setRowId(String parameterName, RowId x) throws SQLException {
		this.unwrap(CallableStatement.class).setRowId(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNString(String, String) 
	 */
	@Override
	public void setNString(String parameterName, String value) throws SQLException {
		this.unwrap(CallableStatement.class).setNString(parameterName, value);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNCharacterStream(String, Reader, long) 
	 */
	@Override
	public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setCharacterStream(parameterName, value, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNClob(String, NClob) 
	 */
	@Override
	public void setNClob(String parameterName, NClob value) throws SQLException {
		this.unwrap(CallableStatement.class).setNClob(parameterName, value);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setClob(String, Reader, long) 
	 */
	@Override
	public void setClob(String parameterName, Reader reader, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setClob(parameterName, reader, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBlob(String, InputStream, long) 
	 */
	@Override
	public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setBlob(parameterName, inputStream, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNClob(String, Reader, long) 
	 */
	@Override
	public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setNClob(parameterName, reader, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getNClob(int) 
	 */
	@Override
	public NClob getNClob(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getNClob(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getNClob(String) 
	 */
	@Override
	public NClob getNClob(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getNClob(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setSQLXML(String, SQLXML) 
	 */
	@Override
	public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
		this.unwrap(CallableStatement.class).setSQLXML(parameterName, xmlObject);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getSQLXML(int) 
	 */
	@Override
	public SQLXML getSQLXML(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getSQLXML(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getSQLXML(String) 
	 */
	@Override
	public SQLXML getSQLXML(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getSQLXML(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getNString(int) 
	 */
	@Override
	public String getNString(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getNString(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getNString(String) 
	 */
	@Override
	public String getNString(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getNString(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getNCharacterStream(int) 
	 */
	@Override
	public Reader getNCharacterStream(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getCharacterStream(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getNCharacterStream(String) 
	 */
	@Override
	public Reader getNCharacterStream(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getNCharacterStream(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getCharacterStream(int) 
	 */
	@Override
	public Reader getCharacterStream(int parameterIndex) throws SQLException {
		return this.unwrap(CallableStatement.class).getCharacterStream(parameterIndex);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getCharacterStream(String) 
	 */
	@Override
	public Reader getCharacterStream(String parameterName) throws SQLException {
		return this.unwrap(CallableStatement.class).getCharacterStream(parameterName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBlob(String, Blob) 
	 */
	@Override
	public void setBlob(String parameterName, Blob x) throws SQLException {
		this.unwrap(CallableStatement.class).setBlob(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setClob(String, Clob) 
	 */
	@Override
	public void setClob(String parameterName, Clob x) throws SQLException {
		this.unwrap(CallableStatement.class).setClob(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setAsciiStream(String, InputStream, long) 
	 */
	@Override
	public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setAsciiStream(parameterName, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBinaryStream(String, InputStream, long) 
	 */
	@Override
	public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setBinaryStream(parameterName, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setCharacterStream(String, Reader, long) 
	 */
	@Override
	public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setCharacterStream(parameterName, reader, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setAsciiStream(String, InputStream) 
	 */
	@Override
	public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
		this.unwrap(CallableStatement.class).setAsciiStream(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBinaryStream(String, InputStream) 
	 */
	@Override
	public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
		this.unwrap(CallableStatement.class).setBinaryStream(parameterName, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setCharacterStream(String, Reader) 
	 */
	@Override
	public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
		this.unwrap(CallableStatement.class).setCharacterStream(parameterName, reader);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNCharacterStream(String, Reader) 
	 */
	@Override
	public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
		this.unwrap(CallableStatement.class).setNCharacterStream(parameterName, value);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setClob(String, Clob) 
	 */
	@Override
	public void setClob(String parameterName, Reader reader) throws SQLException {
		this.unwrap(CallableStatement.class).setClob(parameterName, reader);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBlob(String, InputStream) 
	 */
	@Override
	public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
		this.unwrap(CallableStatement.class).setBlob(parameterName, inputStream);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNClob(String, Reader) 
	 */
	@Override
	public void setNClob(String parameterName, Reader reader) throws SQLException {
		this.unwrap(CallableStatement.class).setNClob(parameterName, reader);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getObject(int, Class)  
	 */
	@Override
	public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
		return this.unwrap(CallableStatement.class).getObject(parameterIndex, type);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getObject(String, Class) 
	 */
	@Override
	public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
		return this.unwrap(CallableStatement.class).getObject(parameterName, type);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#executeQuery() 
	 */
	@Override
	public ResultSet executeQuery() throws SQLException {
		return this.unwrap(CallableStatement.class).executeQuery();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#executeUpdate() 
	 */
	@Override
	public int executeUpdate() throws SQLException {
		return this.unwrap(CallableStatement.class).executeUpdate();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNull(int, int) 
	 */
	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		this.unwrap(CallableStatement.class).setNull(parameterIndex, sqlType);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBoolean(int, boolean) 
	 */
	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		this.unwrap(CallableStatement.class).setBoolean(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setByte(int, byte) 
	 */
	@Override
	public void setByte(int parameterIndex, byte x) throws SQLException {
		this.unwrap(CallableStatement.class).setByte(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setShort(int, short) 
	 */
	@Override
	public void setShort(int parameterIndex, short x) throws SQLException {
		this.unwrap(CallableStatement.class).setShort(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setInt(int, int) 
	 */
	@Override
	public void setInt(int parameterIndex, int x) throws SQLException {
		this.unwrap(CallableStatement.class).setInt(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setLong(int, long) 
	 */
	@Override
	public void setLong(int parameterIndex, long x) throws SQLException {
		this.unwrap(CallableStatement.class).setLong(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setFloat(int, float) 
	 */
	@Override
	public void setFloat(int parameterIndex, float x) throws SQLException {
		this.unwrap(CallableStatement.class).setFloat(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setDouble(int, double) 
	 */
	@Override
	public void setDouble(int parameterIndex, double x) throws SQLException {
		this.unwrap(CallableStatement.class).setDouble(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBigDecimal(int, BigDecimal) 
	 */
	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		this.unwrap(CallableStatement.class).setBigDecimal(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setString(int, String) 
	 */
	@Override
	public void setString(int parameterIndex, String x) throws SQLException {
		this.unwrap(CallableStatement.class).setString(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setByte(int, byte) 
	 */
	@Override
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		this.unwrap(CallableStatement.class).setBytes(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setDate(int, Date) 
	 */
	@Override
	public void setDate(int parameterIndex, Date x) throws SQLException {
		this.unwrap(CallableStatement.class).setDate(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setTime(int, Time) 
	 */
	@Override
	public void setTime(int parameterIndex, Time x) throws SQLException {
		this.unwrap(CallableStatement.class).setTime(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setTimestamp(int, Timestamp) 
	 */
	@Override
	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		this.unwrap(CallableStatement.class).setTimestamp(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setAsciiStream(int, InputStream, int) 
	 */
	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		this.unwrap(CallableStatement.class).setAsciiStream(parameterIndex, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setUnicodeStream(int, InputStream, int) 
	 */
	@Override
	@Deprecated
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
		this.unwrap(CallableStatement.class).setUnicodeStream(parameterIndex, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBinaryStream(int, InputStream, int) 
	 */
	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		this.unwrap(CallableStatement.class).setBinaryStream(parameterIndex, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#clearParameters() 
	 */
	@Override
	public void clearParameters() throws SQLException {
		this.unwrap(CallableStatement.class).clearParameters();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setObject(int, Object, int) 
	 */
	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		this.unwrap(CallableStatement.class).setObject(parameterIndex, x, targetSqlType);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setObject(int, Object) 
	 */
	@Override
	public void setObject(int parameterIndex, Object x) throws SQLException {
		this.unwrap(CallableStatement.class).setObject(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#execute() 
	 */
	@Override
	public boolean execute() throws SQLException {
		return this.unwrap(CallableStatement.class).execute();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#addBatch() 
	 */
	@Override
	public void addBatch() throws SQLException {
		this.unwrap(CallableStatement.class).addBatch();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setCharacterStream(int, Reader, int) 
	 */
	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		this.unwrap(CallableStatement.class).setCharacterStream(parameterIndex, reader, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setRef(int, Ref) 
	 */
	@Override
	public void setRef(int parameterIndex, Ref x) throws SQLException {
		this.unwrap(CallableStatement.class).setRef(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBlob(int, Blob) 
	 */
	@Override
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		this.unwrap(CallableStatement.class).setBlob(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setClob(int, Clob) 
	 */
	@Override
	public void setClob(int parameterIndex, Clob x) throws SQLException {
		this.unwrap(CallableStatement.class).setClob(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setArray(int, Array) 
	 */
	@Override
	public void setArray(int parameterIndex, Array x) throws SQLException {
		this.unwrap(CallableStatement.class).setArray(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getMetaData() 
	 */
	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return this.unwrap(CallableStatement.class).getMetaData();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setDate(int, Date, Calendar) 
	 */
	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		this.unwrap(CallableStatement.class).setDate(parameterIndex, x, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setTime(int, Time, Calendar) 
	 */
	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		this.unwrap(CallableStatement.class).setTime(parameterIndex, x, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setTimestamp(int, Timestamp, Calendar) 
	 */
	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		this.unwrap(CallableStatement.class).setTimestamp(parameterIndex, x, cal);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNull(int, int, String) 
	 */
	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		this.unwrap(CallableStatement.class).setNull(parameterIndex, sqlType, typeName);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setURL(int, URL) 
	 */
	@Override
	public void setURL(int parameterIndex, URL x) throws SQLException {
		this.unwrap(CallableStatement.class).setURL(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#getParameterMetaData() 
	 */
	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		return this.unwrap(CallableStatement.class).getParameterMetaData();
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setRowId(int, RowId) 
	 */
	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		this.unwrap(CallableStatement.class).setRowId(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNString(int, String) 
	 */
	@Override
	public void setNString(int parameterIndex, String value) throws SQLException {
		this.unwrap(CallableStatement.class).setNString(parameterIndex, value);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNCharacterStream(int, Reader, long) 
	 */
	@Override
	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setNCharacterStream(parameterIndex, value, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNClob(int, NClob) 
	 */
	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		this.unwrap(CallableStatement.class).setNClob(parameterIndex, value);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setClob(String, Reader, long) 
	 */
	@Override
	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setClob(parameterIndex, reader, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBlob(int, InputStream, long) 
	 */
	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setBlob(parameterIndex, inputStream, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNClob(int, Reader, long) 
	 */
	@Override
	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setNClob(parameterIndex, reader, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setSQLXML(int, SQLXML) 
	 */
	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
		this.unwrap(CallableStatement.class).setSQLXML(parameterIndex, xmlObject);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setObject(int, Object, int, int) 
	 */
	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
		this.unwrap(CallableStatement.class).setObject(parameterIndex, x, targetSqlType, scaleOrLength);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setAsciiStream(int, InputStream, long) 
	 */
	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setAsciiStream(parameterIndex, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBinaryStream(int, InputStream, long) 
	 */
	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setBinaryStream(parameterIndex, x, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setCharacterStream(int, Reader, long) 
	 */
	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
		this.unwrap(CallableStatement.class).setCharacterStream(parameterIndex, reader, length);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setAsciiStream(int, InputStream) 
	 */
	@Override
	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		this.unwrap(CallableStatement.class).setAsciiStream(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBinaryStream(int, InputStream) 
	 */
	@Override
	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		this.unwrap(CallableStatement.class).setBinaryStream(parameterIndex, x);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setCharacterStream(int, Reader) 
	 */
	@Override
	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		this.unwrap(CallableStatement.class).setCharacterStream(parameterIndex, reader);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNCharacterStream(int, Reader) 
	 */
	@Override
	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
		this.unwrap(CallableStatement.class).setNCharacterStream(parameterIndex, value);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setClob(int, Reader) 
	 */
	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		this.unwrap(CallableStatement.class).setClob(parameterIndex, reader);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setBlob(int, InputStream) 
	 */
	@Override
	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
		this.unwrap(CallableStatement.class).setBlob(parameterIndex, inputStream);
	}

	/**
	 * (Non-Javadoc)
	 * 
	 * @see CallableStatement#setNClob(int, Reader)
	 */
	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		this.unwrap(CallableStatement.class).setNClob(parameterIndex, reader);
	}
}
