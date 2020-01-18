/*
 *    Copyright [2020] [lihaifeng,xuhang]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gitee.common.core.constant;

/**
 * @author lihaifeng
 * @date 2017-12-18
 */
public interface SecurityConstants {
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
  String PLATFORM_PREFIX = "platform_";

  /**
   * oauth 相关前缀
   */
  String OAUTH_PREFIX = "oauth:";
  /**
   * 项目的license
   */
  String PLATFORM_LICENSE = "made by platform";

  /**
   * 内部
   */
  String FROM_IN = "Y";

  /**
   * 标志
   */
  String FROM = "from";

  /**
   * OAUTH URL
   */
  String OAUTH_TOKEN_URL = "/oauth/token";

  /**
   * 手机号登录URL
   */
  String SMS_TOKEN_URL = "/mobile/token/sms";

  /**
   * 社交登录URL
   */
  String SOCIAL_TOKEN_URL = "/mobile/token/social";
  /**
   * 自定义登录URL
   */
  String MOBILE_TOKEN_URL = "/mobile/token/*";

  /**
   * 微信获取OPENID
   */
  String WX_AUTHORIZATION_CODE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token" +
      "?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

  /**
   * 微信小程序OPENID
   */
  String MINI_APP_AUTHORIZATION_CODE_URL = "https://api.weixin.qq.com/sns/jscode2session" +
      "?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

  /**
   * 码云获取token
   */
  String GITEE_AUTHORIZATION_CODE_URL = "https://gitee.com/oauth/token?grant_type=" +
      "authorization_code&code=%S&client_id=%s&redirect_uri=" +
      "%s&client_secret=%s";

  /**
   * 开源中国获取token
   */
  String OSC_AUTHORIZATION_CODE_URL = "https://www.oschina.net/action/openapi/token";

  /**
   * 码云获取用户信息
   */
  String GITEE_USER_INFO_URL = "https://gitee.com/api/v5/user?access_token=%s";

  /**
   * 开源中国用户信息
   */
  String OSC_USER_INFO_URL = "https://www.oschina.net/action/openapi/user?access_token=%s&dataType=json";

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
      + " from platform_oauth_client_details";

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
   * 用户名字段
   */
  String DETAILS_USERNAME = "username";

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
  String PATH_TOKEN="/token/**";

  /**
   * springboot暴露信息
   */
  String PATH_ACTUATOR="/actuator/**";

  /**
   * springboot暴露信息
   */
  String PATH_API_DOCS="/v2/api-docs";

  String JWT_KEY="platform-key";

  /**
   * dev环境变量
   */
  String DEV="dev";

  /**
   * localhost环境变量
   */
  String LOCALHOST="localhost";

  /**
   * test环境变量
   */
  String TEST="test";

  /**
   * uat环境变量
   */
  String UAT="uat";

  /**
   * prod环境变量
   */
  String PROD="prod";
}
