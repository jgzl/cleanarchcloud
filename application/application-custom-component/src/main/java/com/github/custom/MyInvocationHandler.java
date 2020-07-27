package com.github.custom;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.github.custom.interceptor.Interceptor;

public class MyInvocationHandler implements InvocationHandler {

	private final Object target;

	private final Interceptor interceptor;

	public MyInvocationHandler(Object target, Interceptor interceptor) {
		this.target = target;
		this.interceptor = interceptor;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Invocation invocation = new Invocation(target, method, args);
		return interceptor.intercept(invocation);
	}

	public static Object wrap(Object target, Interceptor interceptor) {
		MyInvocationHandler targetProxy = new MyInvocationHandler(target, interceptor);
		return Proxy
				.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), targetProxy);
	}
}
