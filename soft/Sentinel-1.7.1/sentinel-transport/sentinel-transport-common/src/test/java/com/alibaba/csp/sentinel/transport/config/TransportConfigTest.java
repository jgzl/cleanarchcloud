/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.transport.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.util.StringUtil;

public class TransportConfigTest {

	@Before
	public void setUp() throws Exception {
		SentinelConfig.removeConfig(TransportConfig.HEARTBEAT_INTERVAL_MS);
		SentinelConfig.removeConfig(TransportConfig.HEARTBEAT_CLIENT_IP);
	}

	@After
	public void tearDown() throws Exception {
		SentinelConfig.removeConfig(TransportConfig.HEARTBEAT_INTERVAL_MS);
		SentinelConfig.removeConfig(TransportConfig.HEARTBEAT_CLIENT_IP);
	}

	@Test
	public void testGetHeartbeatInterval() {
		long interval = 20000;
		assertNull(TransportConfig.getHeartbeatIntervalMs());
		// Set valid interval.
		SentinelConfig.setConfig(TransportConfig.HEARTBEAT_INTERVAL_MS, String.valueOf(interval));
		assertEquals(new Long(interval), TransportConfig.getHeartbeatIntervalMs());
		// Set invalid interval.
		SentinelConfig.setConfig(TransportConfig.HEARTBEAT_INTERVAL_MS, "Sentinel");
		assertNull(TransportConfig.getHeartbeatIntervalMs());
	}

	@Test
	public void testGetHeartbeatClientIp() {
		String clientIp = "10.10.10.10";
		SentinelConfig.setConfig(TransportConfig.HEARTBEAT_CLIENT_IP, clientIp);
		// Set heartbeat client ip to system property.
		String ip = TransportConfig.getHeartbeatClientIp();

		assertNotNull(ip);
		assertEquals(clientIp, ip);

		// Set no heartbeat client ip.
		SentinelConfig.setConfig(TransportConfig.HEARTBEAT_CLIENT_IP, "");
		assertTrue(StringUtil.isNotEmpty(TransportConfig.getHeartbeatClientIp()));
	}

	@Test
	public void testGetHeartbeatApiPath() {
		// use default heartbeat api path
		assertTrue(StringUtil.isNotEmpty(TransportConfig.getHeartbeatApiPath()));
		assertEquals(TransportConfig.HEARTBEAT_DEFAULT_PATH, TransportConfig.getHeartbeatApiPath());

		// config heartbeat api path
		SentinelConfig.setConfig(TransportConfig.HEARTBEAT_API_PATH, "/demo");
		assertTrue(StringUtil.isNotEmpty(TransportConfig.getHeartbeatApiPath()));
		assertEquals("/demo", TransportConfig.getHeartbeatApiPath());

		SentinelConfig.setConfig(TransportConfig.HEARTBEAT_API_PATH, "demo/registry");
		assertEquals("/demo/registry", TransportConfig.getHeartbeatApiPath());

		SentinelConfig.removeConfig(TransportConfig.HEARTBEAT_API_PATH);
		assertEquals(TransportConfig.HEARTBEAT_DEFAULT_PATH, TransportConfig.getHeartbeatApiPath());
	}
}
