package com.github.optimize.interceptor.impl;

import java.util.Arrays;

import com.github.optimize.Invocation;
import com.github.optimize.interceptor.Interceptor;

public class StatsInterceptor implements Interceptor {
	@Override
	public void before(Invocation invocation) {
		System.out.println("stats----" + Arrays.toString(invocation.getArgs()));
	}

	@Override
	public void after(Invocation invocation, Object result) throws Exception {
		System.out.println("stats result---" + result);
	}
}
