package com.github.jgzl.common.data.datascope;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * 支持自定义数据权限方法注入
 *
 * @author lihaifeng
 * @date 2020-06-17
 */
public class DataScopeSqlInjector extends DefaultSqlInjector {

	public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
		List<AbstractMethod> methodList = super.getMethodList(mapperClass, null);
		methodList.add(new SelectListByScope());
		methodList.add(new SelectPageByScope());
		methodList.add(new SelectCountByScope());
		methodList.add(new InsertBatchSomeColumn());
		return methodList;
	}

}
