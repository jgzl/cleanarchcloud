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

drop database if exists `sys`;
create database `sys` charset utf8mb4;
use `sys`;

/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : sso

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 25/02/2020 02:27:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_used` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NOT NULL COMMENT '上级部门id',
  `dept_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `order_num` double(20, 0) NULL DEFAULT NULL COMMENT '排序',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` bigint(20) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, '开发部', 1, 0, '2020-01-12 08:51:13', 0, '2020-01-12 08:51:13', 0, 0);
INSERT INTO `sys_dept` VALUES (2, 1, '开发一部', 1, 0, '2020-01-12 08:51:13', 0, '2020-01-12 08:51:13', 0, 0);
INSERT INTO `sys_dept` VALUES (3, 1, '开发二部', 2, 0, '2020-01-12 08:51:13', 0, '2020-01-12 08:51:13', 0, 0);
INSERT INTO `sys_dept` VALUES (4, 0, '市场部', 2, 0, '2020-01-12 08:51:13', 0, '2020-01-12 08:51:13', 0, 0);
INSERT INTO `sys_dept` VALUES (5, 0, '人事部', 3, 0, '2020-01-12 08:51:13', 0, '2020-01-12 08:51:13', 0, 0);
INSERT INTO `sys_dept` VALUES (6, 0, '测试部', 4, 0, '2020-01-12 08:51:13', 0, '2020-01-12 08:51:13', 0, 0);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作用户',
  `operation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作内容',
  `time` decimal(11, 0) NULL DEFAULT NULL COMMENT '耗时',
  `method` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作方法',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '方法参数',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作者ip',
  `location` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作地点',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` bigint(20) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮id',
  `parensys_id` bigint(20) NOT NULL COMMENT '上级菜单id',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单/按钮名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对应路由path',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对应路由组件component',
  `perms` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型 0菜单 1按钮',
  `order_num` decimal(20, 0) NULL DEFAULT NULL COMMENT '排序',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` bigint(20) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '/system', 'layout', NULL, 'el-icon-set-up', '0', 1, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (2, 0, '系统监控', '/monitor', 'layout', NULL, 'el-icon-data-line', '0', 2, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (3, 1, '用户管理', '/system/user', 'platform/system/user/index', 'user:view', '', '0', 1, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (4, 1, '角色管理', '/system/role', 'platform/system/role/index', 'role:view', '', '0', 2, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (5, 1, '菜单管理', '/system/menu', 'platform/system/menu/index', 'menu:view', '', '0', 3, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (6, 1, '部门管理', '/system/dept', 'platform/system/dept/index', 'dept:view', '', '0', 4, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (7, 2, '系统日志', '/monitor/systemlog', 'febs/monitor/systemlog/index', 'log:view', '', '0', 1, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (11, 3, '新增用户', '', '', 'user:add', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (12, 3, '修改用户', '', '', 'user:update', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (13, 3, '删除用户', '', '', 'user:delete', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (14, 4, '新增角色', '', '', 'role:add', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (15, 4, '修改角色', '', '', 'role:update', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (16, 4, '删除角色', '', '', 'role:delete', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (17, 5, '新增菜单', '', '', 'menu:add', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (18, 5, '修改菜单', '', '', 'menu:update', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (19, 5, '删除菜单', '', '', 'menu:delete', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (20, 6, '新增部门', '', '', 'dept:add', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (21, 6, '修改部门', '', '', 'dept:update', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_menu` VALUES (22, 6, '删除部门', '', '', 'dept:delete', NULL, '1', NULL, 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);

-- ----------------------------
-- Table structure for sys_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client_details`;
CREATE TABLE `sys_oauth_client_details`  (
  `client_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '终端信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oauth_client_details
-- ----------------------------
INSERT INTO `sys_oauth_client_details` VALUES ('app', 'app', NULL, 'app', 'server', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://www.baidu.com', '', 43200, 2592001, '{\"website\":\"https://blog.dlihaifeng.cn\",\"appName\":\"APP\"}', '', 0, 0);
INSERT INTO `sys_oauth_client_details` VALUES ('codegen', 'codegen', NULL, 'codegen', 'server', 'password,refresh_token', NULL, '', NULL, NULL, NULL, '', 0, 0);
INSERT INTO `sys_oauth_client_details` VALUES ('daemon', 'daemon', NULL, 'daemon', 'server', 'password,refresh_token', NULL, '', NULL, NULL, NULL, '', 0, 0);
INSERT INTO `sys_oauth_client_details` VALUES ('ssoclient1', 'ssoclient1', NULL, 'ssoclient1', 'sso', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://localhost:8071/sso/login,http://127.0.0.1:8071/sso/login,http://client.sso-1.com/sso/login', '', 432000, 2592001, '{\"website\":\"http://localhost:8071/sso\",\"appName\":\"单点登录客户端1\"}', 'true', 0, 0);
INSERT INTO `sys_oauth_client_details` VALUES ('ssoclient2', 'ssoclient2', NULL, 'ssoclient2', 'sso', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://localhost:8072/sso/login,http://127.0.0.1:8072/sso/login,http://client.sso-2.com/sso/login', '', 432000, 2592001, '{\"website\":\"http://localhost:8072/sso\",\"appName\":\"单点登录客户端2\"}', 'true', 0, 0);
INSERT INTO `sys_oauth_client_details` VALUES ('test', 'test', NULL, 'test', 'server', 'password,refresh_token', NULL, '', NULL, NULL, NULL, '', 0, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` bigint(20) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '管理员', 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_role` VALUES (2, '注册用户', '可查看，新增，导出', 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);
INSERT INTO `sys_role` VALUES (3, '系统监控员', '负责系统监控模块', 0, '2020-01-12 08:51:14', 0, '2020-01-12 08:51:14', 0, 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 7);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户姓名(账户)',
  `nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `login_count` int(11) NOT NULL DEFAULT 0 COMMENT '登录次数',
  `login_error_count` int(11) NOT NULL DEFAULT 0 COMMENT '登录错误次数',
  `login_time` datetime(0) NOT NULL COMMENT '登录时间(最新)',
  `login_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '账号状态',
  `avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'https://upload.jianshu.io/users/upload_avatars/2631077/dc99c361412c?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96' COMMENT '头像',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` bigint(20) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_sys_user_username`(`username`) USING BTREE,
  INDEX `idx_sys_user_email`(`email`) USING BTREE,
  INDEX `idx_sys_user_mobile`(`mobile`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '单点登录用户表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (0, 'admin', 'admin-nickname', '{bcrypt}$2a$10$UN122m92j4urCWio9CkMrOYyODshc.RSEiap7x/kJiFw838v0Odsu', '17000000000', 'li7hai26@gmail.com', 0, 0, '2020-01-09 17:31:27', 0, 'https://upload.jianshu.io/users/upload_avatars/2631077/dc99c361412c?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96', 0, 0, '2020-01-09 17:31:28', 0, '2020-01-09 17:31:28', 0);

-- ----------------------------
-- Table structure for sys_user_connection
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_connection`;
CREATE TABLE `sys_user_connection`  (
  `user_connection_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '平台系统用户关联第三方用户id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '平台系统用户名',
  `provider_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '第三方平台名称',
  `provider_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '第三方平台账户id',
  `provider_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方平台用户名',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方平台昵称',
  `image_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方平台头像',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志',
  PRIMARY KEY (`user_connection_id`) USING BTREE,
  UNIQUE INDEX `idx_user_connection`(`user_name`, `provider_name`, `provider_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '平台系统用户关联第三方用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_connection
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (0, 1);
INSERT INTO `sys_user_role` VALUES (0, 2);
INSERT INTO `sys_user_role` VALUES (0, 3);

-- ----------------------------
-- Table structure for sys_route_conf
-- ----------------------------
DROP TABLE IF EXISTS `sys_route_conf`;
CREATE TABLE `sys_route_conf` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `route_name` varchar(30) DEFAULT NULL,
  `route_id` varchar(30) DEFAULT NULL,
  `predicates` json DEFAULT NULL COMMENT '断言',
  `filters` json DEFAULT NULL COMMENT '过滤器',
  `uri` varchar(50) DEFAULT NULL,
  `order` int(2) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='路由配置表';

-- ----------------------------
-- Records of sys_route_conf
-- ----------------------------
BEGIN;
INSERT INTO `sys_route_conf` VALUES (1, '认证中心', 'cleanarch-business-auth', '[{\"args\": {\"_genkey_0\": \"/auth/**\"}, \"name\": \"Path\"}]', '[]', 'lb://cleanarch-business-auth', 0, '2019-10-16 16:44:41', '2019-11-05 22:36:57', '0');
INSERT INTO `sys_route_conf` VALUES (2, '代码生成模块', 'cleanarch-business-codegen', '[{\"args\": {\"_genkey_0\": \"/codegen/**\"}, \"name\": \"Path\"}]', '[]', 'lb://cleanarch-business-codegen', 0, '2019-10-16 16:44:41', '2019-11-05 22:36:58', '0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
