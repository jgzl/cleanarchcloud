package com.github.jgzl.infra.codegen;
import com.github.jgzl.common.feign.annotation.EnableFeignClientsPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.github.jgzl.common.security.annotation.EnableResourceServerPlus;
/**
 * @author lihaifeng
 */
@EnableResourceServerPlus
@EnableFeignClientsPlus
@EnableDiscoveryClient
@SpringBootApplication
public class ToolsApplication {
	public static void main(String[] args) {
		SpringApplication.run(ToolsApplication.class, args);
	}
}
