/*
 * Copyright [2020] [lihaifeng,xuhang]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.jgzl.application.codegen;

import com.github.jgzl.swagger.annotation.EnableSwaggerPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.github.jgzl.common.security.annotation.EnableFeignClientsPlus;
import com.github.jgzl.common.security.annotation.EnableResourceServerPlus;

/**
 * @author lihaifeng
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableSwaggerPlus
@EnableFeignClientsPlus
@EnableResourceServerPlus
public class CodegenApplication {
	public static void main(String[] args) {
		SpringApplication.run(CodegenApplication.class, args);
	}
}
