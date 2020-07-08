package com.gitee.application.client.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gitee.application.client.ws.StudentWebServiceImpl;

@Configuration
public class WebServiceConfig {
	@Autowired
	private Bus bus;

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, new StudentWebServiceImpl());
		endpoint.publish("/student");
		return endpoint;
	}
}
