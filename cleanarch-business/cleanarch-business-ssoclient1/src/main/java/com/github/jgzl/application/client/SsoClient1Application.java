/*
 *    Copyright [2020] [lihaifeng,xuhang]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.jgzl.application.client;

import com.github.jgzl.swagger.annotation.EnableSwaggerPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.github.jgzl.common.security.annotation.EnableFeignClientsPlus;

/**
 * @author lihaifeng
 * 登录http://localhost:8030/login/username/login
 * 输入账号密码 admin admin
 */
@EnableSwaggerPlus
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClientsPlus
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SsoClient1Application {
	public static void main(String[] args) {
		SpringApplication.run(SsoClient1Application.class, args);
	}
}
