package com.github.custom.interceptor;

import com.github.custom.AbstractInterceptor;
import com.github.custom.Invocation;

public class MyTransactionInterceptor extends AbstractInterceptor {
	@Override
	public Object intercept(Invocation invocation) throws Exception {
		System.out.println("------tx before-------------");
		Object result = invocation.process();
		System.out.println("------tx end-------------");
		return result;
	}
}
