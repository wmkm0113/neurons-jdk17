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

package org.nervousync.database.neurons.test;

import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.*;
import org.nervousync.database.neurons.NeuronsDataSource;
import org.nervousync.database.neurons.connection.NeuronsConnection;
import org.nervousync.utils.LoggerUtils;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class DataSourceTest {

	private NeuronsDataSource dataSource;

	@BeforeAll
	public static void init() {
		LoggerUtils.initLoggerConfigure(Level.DEBUG);
	}

	@Order(0)
	@Test
	public void create() {
		this.dataSource = new NeuronsDataSource(2, 10, 1, 2, -1,
				Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, 3,
				"jdbc:derby:D:/nervousync;create=true", new Properties(), "", "");
	}

	@Order(10)
	@Test
	public void obtain() throws SQLException {
		Connection connection = this.dataSource.getConnection();
		this.dataSource.closeConnection((NeuronsConnection) connection);
	}

	@Order(20)
	@Test
	public void multiThreadObtain() throws InterruptedException {
		this.multiThreads(8);
	}

	@Order(30)
	@Test
	public void multiThreadObtainException() throws InterruptedException {
		this.multiThreads(15);
	}

	@Order(100)
	@Test
	public void close() {
		this.dataSource.close();
	}

	private void multiThreads(int threadCount) {
		if (threadCount < 0) {
			return;
		}
		List<ObtainConnectionThread> threadList = new ArrayList<>();
		for (int i = 0; i < threadCount; i++) {
			threadList.add(new ObtainConnectionThread());
		}
		threadList.forEach(Thread::start);
		while (true) {
			if (threadList.stream().noneMatch(Thread::isAlive)) {
				break;
			}
		}
	}

	private final class ObtainConnectionThread extends Thread {

		public ObtainConnectionThread() {
			this.setDaemon(Boolean.TRUE);
		}

		@Override
		public void run() {
			try {
				Connection connection = dataSource.getConnection();
				System.out.println("Obtain connection succeed");
				Thread.sleep(new SecureRandom().nextInt(2) * 1000L);
				dataSource.closeConnection((NeuronsConnection) connection);
			} catch (SQLException | InterruptedException e) {
				System.out.println("Obtain connection failed");
			}
		}
	}
}
