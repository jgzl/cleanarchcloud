package com.github.custom;

import com.github.custom.interceptor.InterceptorChain;
import com.github.custom.interceptor.MyLogInterceptor;
import com.github.custom.interceptor.MyTransactionInterceptor;
import com.github.custom.service.HelloService;
import com.github.custom.service.impl.HelloServiceImpl;

public class Demo {
	public static void main(String[] args) {
		HelloService target = new HelloServiceImpl();
		MyLogInterceptor interceptor1 = new MyLogInterceptor();
		MyTransactionInterceptor interceptor2 = new MyTransactionInterceptor();
		InterceptorChain chain = new InterceptorChain();
		chain.addInterceptor(interceptor1);
		chain.addInterceptor(interceptor2);
		target = (HelloService) chain.pluginAll(target);
		target.sayHello();
	}
}
