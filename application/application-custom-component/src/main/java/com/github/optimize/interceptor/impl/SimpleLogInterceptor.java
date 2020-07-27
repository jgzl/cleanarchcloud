package com.github.optimize.interceptor.impl;

import java.util.Arrays;

import com.github.optimize.Invocation;
import com.github.optimize.interceptor.Interceptor;

public class SimpleLogInterceptor implements Interceptor {
	@Override
	public void before(Invocation invocation) {
		System.out.println("param----" + Arrays.toString(invocation.getArgs()));
	}

	@Override
	public void after(Invocation invocation, Object result) throws Exception {
		System.out.println("result---" + result);
	}
}
