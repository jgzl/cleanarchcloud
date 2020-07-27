package com.github.custom.interceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InterceptorChain {
	private List<Interceptor> interceptorList=new ArrayList<>();

	public Object pluginAll(Object target){
		for (Interceptor interceptor : getInterceptorList()) {
			target = interceptor.plugin(target);
		}
		return target;
	}

	public void addInterceptor(Interceptor interceptor){
		interceptorList.add(interceptor);
	}

	public List<Interceptor> getInterceptorList(){
		return Collections.unmodifiableList(interceptorList);
	}
}
