drop database if exists `platform-core`;
create database `platform-core` charset utf8mb4;
USE `platform-core`;


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

 Date: 10/01/2020 16:46:43
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
-- Table structure for coupon_entity
-- ----------------------------
DROP TABLE IF EXISTS `coupon_entity`;
CREATE TABLE `coupon_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(20) DEFAULT NULL COMMENT '模板id',
  `coupon_name` varchar(50) NOT NULL COMMENT '优惠券显示名称',
  `cover_image_url` varchar(100) DEFAULT NULL COMMENT '封面图片',
  `user_id` varchar(50) DEFAULT NULL,
  `coupon_type` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '类型：1=满减券,2=折扣券',
  `full_limit` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '满额限制：0不限制',
  `face_value` decimal(8,2) unsigned DEFAULT NULL COMMENT '面额：优惠金额或者折扣',
  `scope_of_application_type` int(11) NOT NULL DEFAULT '0' COMMENT '适用范围类型：1=指定商品，2=类目，3=品牌，0不限制',
  `scope_of_application` varchar(255) NOT NULL COMMENT '适用范围',
  `validity_period_type` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '有效期类型：1=固定时间，2=领取后x天有效',
  `begin_time` datetime DEFAULT NULL COMMENT '有效期开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '有效期开始时间',
  `version` bigint(20) NOT NULL DEFAULT '0',
  `record_status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `sort_priority` int(11) DEFAULT '0',
  `remark` varchar(255) DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for coupon_template_entity
-- ----------------------------
DROP TABLE IF EXISTS `coupon_template_entity`;
CREATE TABLE `coupon_template_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(50) NOT NULL COMMENT '模板名称',
  `coupon_name` varchar(50) NOT NULL COMMENT '优惠券显示名称',
  `cover_image_url` varchar(100) DEFAULT NULL COMMENT '封面图片',
  `coupon_type` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '类型：1=满减券,2=折扣券',
  `full_limit` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '满额限制：0不限制',
  `face_value` decimal(8,2) unsigned DEFAULT NULL COMMENT '面额：优惠金额或者折扣',
  `scope_of_application_type` int(11) NOT NULL DEFAULT '0' COMMENT '适用范围类型：1=指定商品，2=类目，3=品牌，0不限制',
  `scope_of_application` varchar(255) NOT NULL COMMENT '适用范围',
  `validity_period_type` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '有效期类型：1=固定时间，2=领取后x天有效',
  `begin_time` datetime DEFAULT NULL COMMENT '有效期开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '有效期开始时间',
  `valid_period_hours` int(11) NOT NULL DEFAULT '0' COMMENT '有效期小时数',
  `maximum_quantity` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大数量',
  `maximum_per_user` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '每位用户最多领取个数：0不限制',
  `quantity_issued` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '已经发放数量',
  `version` bigint(20) NOT NULL DEFAULT '0',
  `record_status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `sort_priority` int(11) DEFAULT '0',
  `remark` varchar(255) DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_template_name` (`template_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  `web_server_redirect_uri` varchar(10000) DEFAULT NULL,
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
INSERT INTO `platform_oauth_client_details` VALUES ('app', NULL, 'app', 'server', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://localhost:8071/sso/login,http://localhost:8072/sso/login,http://127.0.0.1:8071/sso/login,http://127.0.0.1:8072/sso/login', '', 43200, 2592001, NULL, 'true');
INSERT INTO `platform_oauth_client_details` VALUES ('codegen', NULL, 'codegen', 'server', 'password,refresh_token', NULL, '', NULL, NULL, NULL, '');
INSERT INTO `platform_oauth_client_details` VALUES ('daemon', NULL, 'daemon', 'server', 'password,refresh_token', NULL, '', NULL, NULL, NULL, '');
INSERT INTO `platform_oauth_client_details` VALUES ('ssoclient1', NULL, 'ssoclient1', 'server', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://localhost:8071/sso/login,http://127.0.0.1:8071/sso/login', '', 432000, 2592001, NULL, 'true');
INSERT INTO `platform_oauth_client_details` VALUES ('ssoclient2', NULL, 'ssoclient2', 'server', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://localhost:8072/sso/login,http://127.0.0.1:8072/sso/login', '', 432000, 2592001, NULL, 'true');
INSERT INTO `platform_oauth_client_details` VALUES ('test', NULL, 'test', 'server', 'password,refresh_token', NULL, '', NULL, NULL, NULL, '');
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

-- ----------------------------
-- Records of platform_sso_user
-- ----------------------------
BEGIN;
INSERT INTO `platform_sso_user` VALUES (-1, 'test', 'test-nickname', '$2a$10$T8HuULDVyTVfxOTzApSMEOYrbW33NTyL.KSEbQPqZhHeUQxynYJf.', '17621006318', 'test@test.com', 0, 0, '2020-01-10 01:17:48', 0, '\"https://upload.jianshu.io/users/upload_avatars/2631077/dc99c361412c?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96\"', 0, -1, '2020-01-10 01:17:48', -1, '2020-01-10 01:17:48');
INSERT INTO `platform_sso_user` VALUES (0, 'admin', 'admin-nickname', '$2a$10$UN122m92j4urCWio9CkMrOYyODshc.RSEiap7x/kJiFw838v0Odsu', '17000000000', '', 0, 0, '2020-01-09 17:31:27', 0, '', 0, 0, '2020-01-09 17:31:28', 0, '2020-01-09 17:31:28');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;