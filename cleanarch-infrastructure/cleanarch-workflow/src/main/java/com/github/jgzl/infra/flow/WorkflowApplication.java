package com.github.jgzl.infra.flow;

import com.github.jgzl.common.feign.annotation.EnableFeignClientsPlus;
import com.github.jgzl.common.security.annotation.EnableResourceServerPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * # camunda后台访问地址，默认/无权限，无法自动重定向
 * # http://localhost:8014/camunda/app/welcome/default/#!/login
 *
 * @author lihaifeng
 */
@EnableResourceServerPlus
@EnableFeignClientsPlus
@EnableDiscoveryClient
@SpringBootApplication
public class WorkflowApplication {
	public static void main(String[] args) {
		SpringApplication.run(WorkflowApplication.class, args);
	}
}
