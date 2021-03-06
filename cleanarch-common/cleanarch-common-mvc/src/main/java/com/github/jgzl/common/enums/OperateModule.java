package com.github.jgzl.common.enums;

public enum OperateModule {

	SYSTEM("系统模块"),
	BASE_DATA("基础数据模块"),
	BUSINESS1("业务一模块"),
	BUSINESS2("业务一模块");

	String content;

	OperateModule(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
