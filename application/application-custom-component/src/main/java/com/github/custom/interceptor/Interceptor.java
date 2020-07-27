package com.github.custom.interceptor;

import com.github.custom.Invocation;

public interface Interceptor {
	Object intercept(Invocation invocation) throws Exception;
	Object plugin(Object target);
}
