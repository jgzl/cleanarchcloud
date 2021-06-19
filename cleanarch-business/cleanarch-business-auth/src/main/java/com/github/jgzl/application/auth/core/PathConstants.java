package com.github.jgzl.application.auth.core;

public class PathConstants {


	/**
	 * 手机号获取验证码，登录成功获取token的请求路径
	 */
	public static final String OAUTH_MOBILE = "/oauth/mobile";

	/**
	 * 手机号获取验证码，登录成功的请求路径
	 */
	public static final String LOGIN_MODULE_MOBILE_PATH = "/login/mobile";
	public static final String LOGIN_MOBILE_URL = LOGIN_MODULE_MOBILE_PATH+"/login";
	public static final String LOGIN_MOBILE_CODE_URL = PathConstants.LOGIN_MODULE_MOBILE_PATH+"/code";

	public static final String LOGIN_MODULE_PATH = "/login/normal";
	public static final String LOGIN_URL = LOGIN_MODULE_PATH+"/login";
	public static final String LOGIN_FORM_URL = LOGIN_MODULE_PATH+"/form";
	public static final String LOGIN_CONFIRM_URL = LOGIN_MODULE_PATH+"/confirm_access";
	public static final String LOGIN_LOGOUT_URL = LOGIN_MODULE_PATH+"/oauth/exit";
}
