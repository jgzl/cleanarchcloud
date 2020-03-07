/*
 * Copyright 1999-2019 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.demo.spring.webmvc.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test controller
 * @author kaizi2009
 */
@RestController
public class WebMvcTestController {

	@GetMapping("/hello")
	public String apiHello() {
		doBusiness();
		return "Hello!";
	}

	@GetMapping("/err")
	public String apiError() {
		doBusiness();
		return "Oops...";
	}

	@GetMapping("/foo/{id}")
	public String apiFoo(@PathVariable("id") Long id) {
		doBusiness();
		return "Hello " + id;
	}

	@GetMapping("/exclude/{id}")
	public String apiExclude(@PathVariable("id") Long id) {
		doBusiness();
		return "Exclude " + id;
	}

	private void doBusiness() {
		Random random = new Random(1);
		try {
			TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}