package com.github.jgzl.application.auth.core;

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

	public static final String LOGIN_MODULE_PATH = "/login/username";
	public static final String LOGIN_PAGE_URL = LOGIN_MODULE_PATH+"/page";
	public static final String LOGIN_URL = LOGIN_MODULE_PATH+"/login";
	public static final String LOGIN_CONFIRM_URL = LOGIN_MODULE_PATH+"/confirm_access";
	public static final String LOGIN_LOGOUT_URL = LOGIN_MODULE_PATH+"/oauth/exit";
}
