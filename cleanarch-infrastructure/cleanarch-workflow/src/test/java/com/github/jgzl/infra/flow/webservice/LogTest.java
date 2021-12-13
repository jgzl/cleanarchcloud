package com.github.jgzl.infra.flow.webservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

@Slf4j
public class LogTest {

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
