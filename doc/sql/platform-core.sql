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

/*
 Navicat Premium Data Transfer

 Source Server         : docker-local
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : platform-core

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 09/01/2020 00:59:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for PDMAN_DB_VERSION
-- ----------------------------
DROP TABLE IF EXISTS `PDMAN_DB_VERSION`;
CREATE TABLE `PDMAN_DB_VERSION` (
  `DB_VERSION` varchar(256) DEFAULT NULL,
  `VERSION_DESC` varchar(1024) DEFAULT NULL,
  `CREATED_TIME` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of PDMAN_DB_VERSION
-- ----------------------------
BEGIN;
INSERT INTO `PDMAN_DB_VERSION` VALUES ('v0.0.0', '默认版本，新增的版本不能低于此版本', '2020-01-06 20:21:30');
INSERT INTO `PDMAN_DB_VERSION` VALUES ('v1.0.0', '初始化', '2020-01-06 20:21:34');
INSERT INTO `PDMAN_DB_VERSION` VALUES ('v1.0.1', '更新版本', '2020-01-06 20:28:26');
INSERT INTO `PDMAN_DB_VERSION` VALUES ('v1.0.2', '更新默认值', '2020-01-06 23:11:46');
COMMIT;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
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

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastModifiedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
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

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` VALUES ('app', NULL, '$2a$10$/I15oMYYP6Yd1vdgbbhmCOQBKK6mFISdIWy50nVn9agxH4KUqLZ4.', 'server', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://www.baidu.com,http://localhost:8070/sso/login,http://localhost:8071/sso/login,http://localhost:8072/sso/login,http://localhost:8073/sso/login', 'ROLE_ADMIN', NULL, NULL, NULL, 'false');
COMMIT;

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for platform_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `platform_oauth_client_details`;
CREATE TABLE `platform_oauth_client_details` (
  `client_id` varchar(32) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='终端信息表';

-- ----------------------------
-- Records of platform_oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `platform_oauth_client_details` VALUES ('app', NULL, 'app', 'server', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://www.baidu.com,http://localhost:8070/sso/login,http://127.0.0.1:8070/sso/login', 'ROLE_ADMIN', 43200, 2592001, NULL, 'true');
INSERT INTO `platform_oauth_client_details` VALUES ('codegen', NULL, 'codegen', 'server', 'password,refresh_token', NULL, 'ROLE_ADMIN', NULL, NULL, NULL, 'true');
INSERT INTO `platform_oauth_client_details` VALUES ('daemon', NULL, 'daemon', 'server', 'password,refresh_token', NULL, 'ROLE_ADMIN', NULL, NULL, NULL, 'true');
INSERT INTO `platform_oauth_client_details` VALUES ('demo', NULL, 'demo', 'server', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://www.baidu.com,http://localhost:8070/sso/login,http://127.0.0.1:8070/sso/login', 'ROLE_ADMIN', 43200, 2592001, NULL, 'true');
INSERT INTO `platform_oauth_client_details` VALUES ('test', NULL, 'test', 'server', 'password,refresh_token', NULL, 'ROLE_ADMIN', NULL, NULL, NULL, 'true');
COMMIT;

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
INSERT INTO `platform_sso_user` VALUES (-1, 'lihaifeng', 'lihaifeng-nick', '$2a$10$fv8..VP5uJXxRQvcyOunN.fFIzxy.vKnQKC5fX1i9DNe4xhMS8mTy', '17621006318', 'li7hai26@gmail.com', 0, 0, '2020-01-06 00:00:00', 0, '', 0, 1, '2020-01-06 00:00:00', 1, '2020-01-06 00:00:00');
INSERT INTO `platform_sso_user` VALUES (1214229486854348802, 'lihaifeng1', 'lihaifeng1-nick', '$2a$10$knJNbYsQXcxqy4o3GJCG5.6Ojd.ujpf17chZDhz2IEEljJu73I/lG', '17621016318', 'li7hai26@gmail.com', 0, 0, '2020-01-07 00:57:24', 0, '', 0, -1, '2020-01-07 00:57:25', -1, '2020-01-07 00:57:25');
INSERT INTO `platform_sso_user` VALUES (1214237069111697410, 'lihaifeng3', 'lihaifeng1-nick', '$2a$10$vArVXSueJ2Kgf7oLPVCSz.tLlA2dvyzA1wcnbGNL1YQrvR6mUxNEC', '17621016318', 'li7hai26@gmail.com', 0, 0, '2020-01-07 01:27:31', 0, '', 0, 1214229486854348802, '2020-01-07 01:27:40', 1214229486854348802, '2020-01-07 01:27:48');
INSERT INTO `platform_sso_user` VALUES (1214509895139889154, 'wuzhiyuan', 'wuzhiyuan', '$2a$10$KJBzhqMCDW0PJMFsN.KMyecTkmy2cNrcQrAWcZnrTjMDwzyjsOx3C', '', '', 0, 0, '2020-01-07 19:31:37', 0, '', 0, -1, '2020-01-07 19:31:50', -1, '2020-01-07 19:31:59');
INSERT INTO `platform_sso_user` VALUES (1214530330174853122, 'xielinfeng', 'xielinfeng-nick', '$2a$10$.RgbqPahLelg4MeSlq9I3ugxvo6e7fo4/Yf8ngMdEd0y/7r/Z28g2', '', '', 0, 0, '2020-01-07 20:52:50', 0, '', 0, -1, '2020-01-07 20:52:50', -1, '2020-01-07 20:52:50');
INSERT INTO `platform_sso_user` VALUES (1214530818630914050, 'guanyong', 'guanyong-nick', '$2a$10$symk5RH3iQchcYMj5ScYWuFypsePdxlRrtgPgyqekQ00L0xTz4g.W', '', '', 0, 0, '2020-01-07 20:54:47', 0, '', 0, -1, '2020-01-07 20:54:47', -1, '2020-01-07 20:54:47');
INSERT INTO `platform_sso_user` VALUES (1214727232044879874, 'demo1', 'demo1-demo1', '$2a$10$UjXtqujVo8WgT34wsq8ymujeDr47K4Q2NaAZ1w76dPuUDjxBekaMO', '', '', 0, 0, '2019-09-10 00:30:00', 0, '', 0, -1, '2020-01-08 09:55:15', -1, '2020-01-08 09:55:15');
INSERT INTO `platform_sso_user` VALUES (1214807153870065665, 'demo10', '10demo', '$2a$10$XVw3dEhJuoyyc8vSFWixM.UM1ViegesgLVNoXog/.lhemEpMgC3Lq', '', '', 0, 0, '2019-09-23 00:01:34', 0, '\"https://upload.jianshu.io/users/upload_avatars/2631077/dc99c361412c?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96\"', 0, -1, '2020-01-08 15:12:50', -1, '2020-01-08 15:12:50');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;