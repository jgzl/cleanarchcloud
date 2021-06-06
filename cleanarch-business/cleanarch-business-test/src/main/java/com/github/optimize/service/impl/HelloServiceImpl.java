package com.github.optimize.service.impl;

import com.github.optimize.service.HelloService;

public class HelloServiceImpl implements HelloService {
	@Override
	public String sayHello(String name) {
		System.out.println("输入参数为："+name);
		return name+name;
	}
}
