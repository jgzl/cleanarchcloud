package com.github.optimize.interceptor;

import com.github.optimize.Invocation;

public interface Interceptor {
	void before(Invocation invocation);
	void after(Invocation invocation,Object result) throws Exception;
}
