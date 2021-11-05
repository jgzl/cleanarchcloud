package com.github.jgzl.infra.upms.core;

public class PathConstants {

	/**
	 * 邮箱获取验证码，登录成功的请求路径
	 */
	public static final String LOGIN_MODULE_EMAIL_PATH = "/login/email";
	public static final String LOGIN_EMAIL_URL = LOGIN_MODULE_EMAIL_PATH+"/login";
	public static final String LOGIN_EMAIL_CODE_URL = PathConstants.LOGIN_MODULE_EMAIL_PATH+"/code";

	/**
	 * 手机号获取验证码，登录成功的请求路径
	 */
	public static final String LOGIN_MODULE_MOBILE_PATH = "/login/mobile";
	public static final String LOGIN_MOBILE_URL = LOGIN_MODULE_MOBILE_PATH+"/login";
	public static final String LOGIN_MOBILE_CODE_URL = PathConstants.LOGIN_MODULE_MOBILE_PATH+"/code";

	/**
	 * 第三方登录，登录成功的请求路径
	 */
	public static final String LOGIN_THIRD_CALLBACK = "/callback";
	public static final String LOGIN_MODULE_THIRD_PATH = "/third/oauth/";
	public static final String LOGIN_THIRD_LOGIN_URL = LOGIN_MODULE_THIRD_PATH+"login/{type}";
	public static final String LOGIN_THIRD_CALLBACK_URL = LOGIN_MODULE_THIRD_PATH+"**"+LOGIN_THIRD_CALLBACK;

	/**
	 * 用户名密码
	 */
	public static final String LOGIN_MODULE_PATH = "/login/username";
	public static final String LOGIN_PAGE_URL = LOGIN_MODULE_PATH+"/index";
	public static final String LOGIN_URL = LOGIN_MODULE_PATH+"/login";

	/**
	 * 授权码模式授权页面
	 */
	public static final String LOGIN_CONFIRM_URL = LOGIN_MODULE_PATH+"/confirm_access";

	/**
	 * controller登出
	 */
	public static final String LOGOUT_SUCCESS_URL = "/logout/revoke/token";

	/**
	 * config登出
	 */
	public static final String LOGOUT_URL = "/logout";
}
