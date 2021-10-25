package com.github.jgzl.common.api.vo;


import lombok.Data;

/**
 * @author lihaifeng
 * @date 2018/8/27 前端日志vo
 */
@Data
public class PreLogVo {

	/**
	 * 请求url
	 */
	private String url;

	/**
	 * 请求耗时
	 */
	private String time;

	/**
	 * 请求用户
	 */
	private String user;

	/**
	 * 请求结果0:成功9:失败
	 */
	private String type;

	/**
	 * 请求传递参数
	 */
	private String message;

	/**
	 * 异常信息
	 */
	private String stack;

	/**
	 * 日志标题
	 */
	private String info;

}
