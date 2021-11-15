package com.github.jgzl.infra.flow;
import com.github.jgzl.common.security.annotation.EnableResourceServerPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * @author lihaifeng
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableResourceServerPlus
public class CamundaApplication {
	public static void main(String[] args) {
		SpringApplication.run(CamundaApplication.class, args);
	}
}
