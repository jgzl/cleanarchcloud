package com.github.optimize;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.github.optimize.interceptor.Interceptor;

public class DefaultInvocationHandler implements InvocationHandler {

	private Object target;

	private Interceptor interceptor;

	public DefaultInvocationHandler(Object target, Interceptor interceptor) {
		this.target = target;
		this.interceptor = interceptor;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Invocation invocation=new Invocation(target,method,args);
		interceptor.before(invocation);
		Object result = method.invoke(target, args);
		interceptor.after(invocation,result);
		return result;
	}

	public static Object proxy(Object target,Interceptor interceptor){
		DefaultInvocationHandler invocationHandler = new DefaultInvocationHandler(target, interceptor);
		return Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(),
				invocationHandler);
	}
}
