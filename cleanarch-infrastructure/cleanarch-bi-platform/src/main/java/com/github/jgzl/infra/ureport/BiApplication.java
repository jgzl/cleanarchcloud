package com.github.jgzl.infra.ureport;

import com.github.jgzl.common.feign.annotation.EnableFeignClientsPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lihaifeng
 * @date 2020-10-15 报表模块
 * <p>
 * 此模块由于使用的是ureport 封装报表设计器，无法区分租户权限较大建议不提供具体租户使用。
 * <p>
 * http://127.0.0.1:5006/ureport/designer
 */
@EnableFeignClientsPlus
@EnableDiscoveryClient
@SpringBootApplication
public class BiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiApplication.class, args);
	}

}
