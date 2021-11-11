package com.github.jgzl.common.core.constant;

/**
 * @author lihaifeng
 * @date 2017-12-18
 */
public interface SecurityConstants {
	/**
	 * 启动时是否检查Inner注解安全性
	 */
	boolean INNER_CHECK = true;
	/**
	 * 刷新
	 */
	String REFRESH_TOKEN = "refresh_token";
	/**
	 * 验证码有效期
	 */
	int CODE_TIME = 60;
	/**
	 * 验证码长度
	 */
	String CODE_SIZE = "4";
	/**
	 * 角色前缀
	 */
	String ROLE = "ROLE_";
	/**
	 * 前缀
	 */
	String APP_PREFIX = "cleanarchcloud_";

	/**
	 * basic
	 */
	String BASIC_HEADER = "Basic ";

	/**
	 * AUTHORIZATION name
	 */
	String AUTHORIZATION = "Authorization";

	/**
	 * 用户名信息头
	 */
	String USER_NAME_HEADER = "x-user-name";

	/**
	 * 用户密码信息头
	 */
	String USER_ID_HEADER = "x-user-id";

	/**
	 * license key
	 */
	String LICENSE_KEY = "license";

	/**
	 * 项目的license
	 */
	String LICENSE = "made by cleanarchcloud";

	/**
	 * oauth 相关前缀
	 */
	String OAUTH_PREFIX = "oauth:";

	/**
	 * 内部
	 */
	String FROM_IN = "Y";

	/**
	 * 标志
	 */
	String FROM = "from";

	/**
	 * {bcrypt} 加密的特征码
	 */
	String BCRYPT = "{bcrypt}";
	String NOOP = "{noop}";
	/**
	 * sys_oauth_client_details 表的字段，不包括client_id、client_secret
	 */
	String CLIENT_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
			+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
			+ "refresh_token_validity, additional_information, autoapprove";

	/**
	 * JdbcClientDetailsService 查询语句
	 */
	String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
			+ " from sys_oauth_client_details";

	/**
	 * 默认的查询语句
	 */
	String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

	/**
	 * 按条件client_id 查询
	 */
	String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

	/**
	 * 资源服务器默认bean名称
	 */
	String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";

	/**
	 * 客户端模式
	 */
	String CLIENT_CREDENTIALS = "client_credentials";


	/**
	 * 用户ID字段
	 */
	String DETAILS_USER_ID = "id";

	/**
	 * 用户名
	 */
	String DETAILS_USERNAME = "username";

	/**
	 * 用户基本信息
	 */
	String DETAILS_USER = "user_info";

	/**
	 * 用户名phone
	 */
	String DETAILS_PHONE = "phone";

	/**
	 * 头像
	 */
	String DETAILS_AVATAR = "avatar";

	/**
	 * 用户部门字段
	 */
	String DETAILS_DEPT_ID = "deptId";

	/**
	 * 租户ID 字段
	 */
	String DETAILS_TENANT_ID = "tenantId";

	/**
	 * 租户ID 字段
	 */
	String DETAILS_TENANT_CODE = "tenantCode";

	/**
	 * 协议字段
	 */
	String DETAILS_LICENSE = "license";

	/**
	 * 激活字段 兼容外围系统接入
	 */
	String ACTIVE = "active";

	/**
	 * AES 加密
	 */
	String AES = "aes";

	/**
	 * springboot暴露信息
	 */
	String PATH_ACTUATOR = "/actuator/**";

	/**
	 * springboot暴露信息
	 */
	String PATH_API_DOCS = "/v2/api-docs";

	/**
	 * dev环境变量
	 */
	String DEV = "dev";

	/**
	 * test环境变量
	 */
	String SIT = "sit";

	/**
	 * uat环境变量
	 */
	String UAT = "uat";

	/**
	 * prod环境变量
	 */
	String PROD = "prod";

	/**
	 * 重定向URL
	 */
	String REDIRECT_URL="redirect_url";
}
