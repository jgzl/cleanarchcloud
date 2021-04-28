package com.github.custom;

import com.github.custom.interceptor.Interceptor;

public class AbstractInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Exception {
		return null;
	}

	@Override
	public Object plugin(Object target) {
		return MyInvocationHandler.wrap(target,this);
	}
}
