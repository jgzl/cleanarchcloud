drop database if exists `platform-core`;
create database `platform-core` default character set utf8mb4;
use `platform-core`;
CREATE TABLE `clientdetails` (
  `appId` varchar(255) NOT NULL,
  `resourceIds` varchar(256) DEFAULT NULL,
  `appSecret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `grantTypes` varchar(256) DEFAULT NULL,
  `redirectUrl` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additionalInformation` varchar(4096) DEFAULT NULL,
  `autoApproveScopes` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastModifiedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(2560) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `oauth_client_details` VALUES ('client', null, '$2a$10$1N/.LvTJuYpvxDzoJ1KdvuPDdV/kDSQE9Cxm9BzB1PreyzK6gmFRe', 'ALL,AUTH,USER,GOODS,ORDER', 'authorization_code,client_credentials,password,refresh_token', 'http://localhost:8081/mall/callback,http://localhost:9080/user/webjars/springfox-swagger-ui/oauth2-redirect.html,http://localhost:9081/goods/webjars/springfox-swagger-ui/oauth2-redirect.html,http://localhost:9082/order/webjars/springfox-swagger-ui/oauth2-redirect.html,http://localhost/user/webjars/springfox-swagger-ui/oauth2-redirect.html,http://localhost/goods/webjars/springfox-swagger-ui/oauth2-redirect.html,http://localhost/order/webjars/springfox-swagger-ui/oauth2-redirect.html', 'ROLE_USER', '1800', '86400', null, 'false');

CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for platform_sso_user
-- ----------------------------
DROP TABLE IF EXISTS `platform_sso_user`;
CREATE TABLE `platform_sso_user` (
  `id` bigint(64) NOT NULL COMMENT '用户id',
  `username` varchar(128) NOT NULL COMMENT '用户姓名(账户)',
  `nickname` varchar(128) NOT NULL COMMENT '用户昵称',
  `password` varchar(128) NOT NULL COMMENT '用户密码',
  `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '手机号',
  `email` varchar(128) NOT NULL DEFAULT '' COMMENT '邮箱',
  `login_count` int(11) NOT NULL DEFAULT '0' COMMENT '登录次数',
  `login_error_count` int(11) NOT NULL DEFAULT '0' COMMENT '登录错误次数',
  `login_time` datetime NOT NULL COMMENT '登录时间(最新)',
  `login_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '账号状态',
  `avatar` varchar(1024) NOT NULL DEFAULT '"https://upload.jianshu.io/users/upload_avatars/2631077/dc99c361412c?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96"' COMMENT '头像',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `created_by` bigint(64) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(64) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sso_user_username` (`username`),
  KEY `idx_sso_user_email` (`email`),
  KEY `idx_sso_user_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单点登录用户表 ';

-- ----------------------------
-- Records of platform_sso_user
-- ----------------------------
BEGIN;
INSERT INTO `platform_sso_user` VALUES (-1, 'lihaifeng', 'lihaifeng-nick', '$2a$10$fv8..VP5uJXxRQvcyOunN.fFIzxy.vKnQKC5fX1i9DNe4xhMS8mTy', '17621006318', 'li7hai26@gmail.com', 0, 0, '2020-01-06 00:00:00', 0, '', 0, -1, '2020-01-06 00:00:00', -1, '2020-01-06 00:00:00');
COMMIT;