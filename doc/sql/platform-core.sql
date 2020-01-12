/*
 * Copyright [2020] [lihaifeng,xuhang]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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

drop database if exists `platform-core`;
create database `platform-core` charset utf8mb4;
use `platform-core`;

/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.5
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 192.168.0.5:3306
 Source Schema         : platform-core

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 12/01/2020 18:07:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for platform_dept
-- ----------------------------
DROP TABLE IF EXISTS `platform_dept`;
CREATE TABLE `platform_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NOT NULL COMMENT '上级部门id',
  `dept_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门名称',
  `order_num` double(20,0) DEFAULT NULL COMMENT '排序',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `revision` int(10) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='部门表';

-- ----------------------------
-- Records of platform_dept
-- ----------------------------
BEGIN;
INSERT INTO `platform_dept` VALUES (1, 0, '开发部', 1, 0, '2020-01-12 08:51:13', 0, '2020-01-12 08:51:13', 0);
INSERT INTO `platform_dept` VALUES (2, 1, '开发一部', 1, 0, '2020-01-12 08:51:13', 0, '2020-01-12 08:51:13', 0);
INSERT INTO `platform_dept` VALUES (3, 1, '开发二部', 2, 0, '2020-01-12 08:51:13', 0, '2020-01-12 08:51:13', 0);
INSERT INTO `platform_dept` VALUES (4, 0, '市场部', 2, 0, '2020-01-12 08:51:13', 0, '2020-01-12 08:51:13', 0);
INSERT INTO `platform_dept` VALUES (5, 0, '人事部', 3, 0, '2020-01-12 08:51:13', 0, '2020-01-12 08:51:13', 0);
INSERT INTO `platform_dept` VALUES (6, 0, '测试部', 4, 0, '2020-01-12 08:51:13', 0, '2020-01-12 08:51:13', 0);
COMMIT;

-- ----------------------------
-- Table structure for platform_log
-- ----------------------------
DROP TABLE IF EXISTS `platform_log`;
CREATE TABLE `platform_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `username` varchar(50) DEFAULT NULL COMMENT '操作用户',
  `operation` text COMMENT '操作内容',
  `time` decimal(11,0) DEFAULT NULL COMMENT '耗时',
  `method` text COMMENT '操作方法',
  `params` text COMMENT '方法参数',
  `ip` varchar(64) DEFAULT NULL COMMENT '操作者ip',
  `location` varchar(50) DEFAULT NULL COMMENT '操作地点',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `revision` int(10) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户操作日志表';

-- ----------------------------
-- Table structure for platform_menu
-- ----------------------------
DROP TABLE IF EXISTS `platform_menu`;
CREATE TABLE `platform_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮id',
  `parenplatform_id` bigint(20) NOT NULL COMMENT '上级菜单id',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单/按钮名称',
  `path` varchar(255) DEFAULT NULL COMMENT '对应路由path',
  `component` varchar(255) DEFAULT NULL COMMENT '对应路由组件component',
  `perms` varchar(50) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `type` char(2) NOT NULL COMMENT '类型 0菜单 1按钮',
  `order_num` decimal(20,0) DEFAULT NULL COMMENT '排序',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `revision` int(10) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='菜单表';

-- ----------------------------
-- Records of platform_menu
-- ----------------------------
BEGIN;
INSERT INTO `platform_menu` VALUES (1, 0, '系统管理', '/system', 'layout', NULL, 'el-icon-set-up', '0', 1, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (2, 0, '系统监控', '/monitor', 'layout', NULL, 'el-icon-data-line', '0', 2, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (3, 1, '用户管理', '/system/user', 'platform/system/user/index', 'user:view', '', '0', 1, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (4, 1, '角色管理', '/system/role', 'platform/system/role/index', 'role:view', '', '0', 2, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (5, 1, '菜单管理', '/system/menu', 'platform/system/menu/index', 'menu:view', '', '0', 3, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (6, 1, '部门管理', '/system/dept', 'platform/system/dept/index', 'dept:view', '', '0', 4, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (7, 2, '系统日志', '/monitor/systemlog', 'febs/monitor/systemlog/index', 'log:view', '', '0', 1, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (11, 3, '新增用户', '', '', 'user:add', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (12, 3, '修改用户', '', '', 'user:update', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (13, 3, '删除用户', '', '', 'user:delete', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (14, 4, '新增角色', '', '', 'role:add', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (15, 4, '修改角色', '', '', 'role:update', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (16, 4, '删除角色', '', '', 'role:delete', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (17, 5, '新增菜单', '', '', 'menu:add', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (18, 5, '修改菜单', '', '', 'menu:update', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (19, 5, '删除菜单', '', '', 'menu:delete', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (20, 6, '新增部门', '', '', 'dept:add', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (21, 6, '修改部门', '', '', 'dept:update', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_menu` VALUES (22, 6, '删除部门', '', '', 'dept:delete', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
COMMIT;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='终端信息表';

-- ----------------------------
-- Records of platform_oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `platform_oauth_client_details` VALUES ('app', NULL, 'app', 'server', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://www.baidu.com', '', 43200, 2592001, '{\"website\":\"https://blog.dlihaifeng.cn\",\"appName\":\"APP\"}', '');
INSERT INTO `platform_oauth_client_details` VALUES ('codegen', NULL, 'codegen', 'server', 'password,refresh_token', NULL, '', NULL, NULL, NULL, '');
INSERT INTO `platform_oauth_client_details` VALUES ('daemon', NULL, 'daemon', 'server', 'password,refresh_token', NULL, '', NULL, NULL, NULL, '');
INSERT INTO `platform_oauth_client_details` VALUES ('ssoclient1', NULL, 'ssoclient1', 'sso', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://localhost:8071/sso/login,http://127.0.0.1:8071/sso/login,http://client.sso-1.com/sso/login', '', 432000, 2592001, '{\"website\":\"http://localhost:8071/sso\",\"appName\":\"单点登录客户端1\"}', 'true');
INSERT INTO `platform_oauth_client_details` VALUES ('ssoclient2', NULL, 'ssoclient2', 'sso', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://localhost:8072/sso/login,http://127.0.0.1:8072/sso/login,http://client.sso-2.com/sso/login', '', 432000, 2592001, '{\"website\":\"http://localhost:8072/sso\",\"appName\":\"单点登录客户端2\"}', 'true');
INSERT INTO `platform_oauth_client_details` VALUES ('test', NULL, 'test', 'server', 'password,refresh_token', NULL, '', NULL, NULL, NULL, '');
COMMIT;

-- ----------------------------
-- Table structure for platform_role
-- ----------------------------
DROP TABLE IF EXISTS `platform_role`;
CREATE TABLE `platform_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(10) NOT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `revision` int(10) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of platform_role
-- ----------------------------
BEGIN;
INSERT INTO `platform_role` VALUES (1, '管理员', '管理员', 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_role` VALUES (2, '注册用户', '可查看，新增，导出', 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
INSERT INTO `platform_role` VALUES (3, '系统监控员', '负责系统监控模块', 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0);
COMMIT;

-- ----------------------------
-- Table structure for platform_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `platform_role_menu`;
CREATE TABLE `platform_role_menu` (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='角色菜单关联表';

-- ----------------------------
-- Records of platform_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `platform_role_menu` VALUES (1, 1);
INSERT INTO `platform_role_menu` VALUES (1, 2);
INSERT INTO `platform_role_menu` VALUES (1, 3);
INSERT INTO `platform_role_menu` VALUES (1, 4);
INSERT INTO `platform_role_menu` VALUES (1, 5);
INSERT INTO `platform_role_menu` VALUES (1, 6);
INSERT INTO `platform_role_menu` VALUES (1, 7);
INSERT INTO `platform_role_menu` VALUES (2, 1);
INSERT INTO `platform_role_menu` VALUES (2, 2);
INSERT INTO `platform_role_menu` VALUES (2, 3);
INSERT INTO `platform_role_menu` VALUES (2, 4);
INSERT INTO `platform_role_menu` VALUES (2, 5);
INSERT INTO `platform_role_menu` VALUES (2, 6);
INSERT INTO `platform_role_menu` VALUES (2, 7);
INSERT INTO `platform_role_menu` VALUES (3, 2);
INSERT INTO `platform_role_menu` VALUES (3, 7);
COMMIT;

-- ----------------------------
-- Table structure for platform_sso_user
-- ----------------------------
DROP TABLE IF EXISTS `platform_sso_user`;
CREATE TABLE `platform_sso_user` (
  `id` bigint(20) NOT NULL COMMENT '用户id',
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
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` bigint(20) NOT NULL COMMENT '更新人',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sso_user_username` (`username`),
  KEY `idx_sso_user_email` (`email`),
  KEY `idx_sso_user_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='单点登录用户表 ';

-- ----------------------------
-- Records of platform_sso_user
-- ----------------------------
BEGIN;
INSERT INTO `platform_sso_user` VALUES (-1, 'test', 'test-nickname', '$2a$10$T8HuULDVyTVfxOTzApSMEOYrbW33NTyL.KSEbQPqZhHeUQxynYJf.', '17621006318', 'test@test.com', 0, 0, '2020-01-10 01:17:48', 0, '\"https://upload.jianshu.io/users/upload_avatars/2631077/dc99c361412c?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96\"', 0, -1, '2020-01-10 01:17:48', -1, '2020-01-10 01:17:48');
INSERT INTO `platform_sso_user` VALUES (0, 'admin', 'admin-nickname', '$2a$10$UN122m92j4urCWio9CkMrOYyODshc.RSEiap7x/kJiFw838v0Odsu', '17000000000', '', 0, 0, '2020-01-09 17:31:27', 0, '', 0, 0, '2020-01-09 17:31:28', 0, '2020-01-09 17:31:28');
COMMIT;

-- ----------------------------
-- Table structure for platform_user_connection
-- ----------------------------
DROP TABLE IF EXISTS `platform_user_connection`;
CREATE TABLE `platform_user_connection` (
  `user_connection_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '平台系统用户关联第三方用户id',
  `user_name` varchar(50) NOT NULL COMMENT '平台系统用户名',
  `provider_name` varchar(20) NOT NULL COMMENT '第三方平台名称',
  `provider_user_id` varchar(50) NOT NULL COMMENT '第三方平台账户id',
  `provider_user_name` varchar(50) DEFAULT NULL COMMENT '第三方平台用户名',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '第三方平台昵称',
  `image_url` varchar(512) DEFAULT NULL COMMENT '第三方平台头像',
  `location` varchar(255) DEFAULT NULL COMMENT '地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `revision` int(10) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  PRIMARY KEY (`user_connection_id`) USING BTREE,
  UNIQUE KEY `idx_user_connection` (`user_name`,`provider_name`,`provider_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='平台系统用户关联第三方用户表';

-- ----------------------------
-- Table structure for platform_user_role
-- ----------------------------
DROP TABLE IF EXISTS `platform_user_role`;
CREATE TABLE `platform_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

-- ----------------------------
-- Records of platform_user_role
-- ----------------------------
BEGIN;
INSERT INTO `platform_user_role` VALUES (0, 1);
INSERT INTO `platform_user_role` VALUES (0, 2);
INSERT INTO `platform_user_role` VALUES (0, 3);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;