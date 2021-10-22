package com.github.jgzl.application.config;

import javax.xml.ws.Endpoint;

import com.github.jgzl.application.ws.StudentWebServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
