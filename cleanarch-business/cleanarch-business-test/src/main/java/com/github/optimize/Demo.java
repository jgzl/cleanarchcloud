package com.github.optimize;
import com.github.optimize.interceptor.InterceptorChain;
import com.github.optimize.interceptor.impl.SimpleLogInterceptor;
import com.github.optimize.interceptor.impl.StatsInterceptor;
import com.github.optimize.service.HelloService;
import com.github.optimize.service.impl.HelloServiceImpl;

public class Demo {
	public static void main(String[] args) {
		HelloService helloService=new HelloServiceImpl();
		InterceptorChain chain = new InterceptorChain();
		chain.add(new StatsInterceptor());
		chain.add(new SimpleLogInterceptor());
		helloService = (HelloService) chain.pluginAll(helloService);
		String result = helloService.sayHello("lihaifeng");
		System.out.println("helloService->result->"+result);
	}
}
