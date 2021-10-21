package com.github.jgzl.infra.flow.ws;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LogTest {

	private static final Logger log = LoggerFactory.getLogger(LogTest.class);

	@Test
	public void logTest(){
		MDC.put("demoKey1","demoKeyValue1");
		MDC.put("demoKey2","demoKeyValue2");
		log.info("测试MDC 1设置值");
		MDC.put("demoKey1","demoKeyValue11");
		MDC.put("demoKey2","demoKeyValue22");
		log.info("测试MDC 2设置值");
	}
}
