drop database if exists `cleanarch`;
create database `cleanarch` charset utf8mb4;
use `cleanarch`;

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
  `create_user` varchar(64) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT  CURRENT_TIMESTAMP '创建时间',
  `update_user` varchar(64) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP '更新时间',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `del_flag` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志',
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
CREATE TABLE `sys_log` (
                           `id` bigint(64) NOT NULL AUTO_INCREMENT,
                           `type` char(1) DEFAULT '0',
                           `title` varchar(255) DEFAULT NULL,
                           `service_id` varchar(32) DEFAULT NULL,
                           `remote_addr` varchar(255) DEFAULT NULL,
                           `user_agent` varchar(1000) DEFAULT NULL,
                           `request_uri` varchar(255) DEFAULT NULL,
                           `method` varchar(10) DEFAULT NULL,
                           `params` text,
                           `time` mediumtext CHARACTER SET utf8 COMMENT '执行时间',
                           `exception` text,
                           `del_flag` char(1) DEFAULT '0',
                           `version` varchar(10) DEFAULT '0',
                           `create_user` varchar(255) DEFAULT NULL,
                           `update_user` varchar(255) DEFAULT NULL,
                           `create_date` datetime DEFAULT CURRENT_TIMESTAMP ,
                           `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                           `tenant_id` int(11) DEFAULT '0' COMMENT '所属租户',
                           PRIMARY KEY (`id`) USING BTREE,
                           KEY `sys_log_create_by` (`create_user`) USING BTREE,
                           KEY `sys_log_request_uri` (`request_uri`) USING BTREE,
                           KEY `sys_log_type` (`type`) USING BTREE,
                           KEY `sys_log_create_date` (`create_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日志表';

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
  `create_user` varchar(64) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT  CURRENT_TIMESTAMP '创建时间',
  `update_user` varchar(64) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP '更新时间',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `del_flag` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志',
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
  `del_flag` char(1) DEFAULT '0',
  `version` varchar(10) DEFAULT '0',
  `create_user` varchar(255) DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP ,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `tenant_id` int(11) DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '终端信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oauth_client_details
-- ----------------------------
INSERT INTO `sys_oauth_client_details` VALUES ('app', 'app', NULL, 'app', 'server', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://www.baidu.com', '', 43200, 2592001, '{\"website\":\"https://blog.dlihaifeng.cn\",\"appName\":\"APP\"}', '', 0, 0,'admin','admin',current_date,current_date,0);
INSERT INTO `sys_oauth_client_details` VALUES ('codegen', 'codegen', NULL, 'codegen', 'server', 'password,refresh_token', NULL, '', NULL, NULL, NULL, '', 0, 0,'admin','admin',current_date,current_date,0);
INSERT INTO `sys_oauth_client_details` VALUES ('daemon', 'daemon', NULL, 'daemon', 'server', 'password,refresh_token', NULL, '', NULL, NULL, NULL, '', 0, 0,'admin','admin',current_date,current_date,0);
INSERT INTO `sys_oauth_client_details` VALUES ('ssoclient1', 'ssoclient1', NULL, 'ssoclient1', 'sso', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://localhost:8101/sso/login,http://127.0.0.1:8101/sso/login,http://client.sso-1.com/sso/login', '', 432000, 2592001, '{\"website\":\"http://localhost:8101/sso\",\"appName\":\"单点登录客户端1\"}', 'true', 0, 0,'admin','admin',current_date,current_date,0);
INSERT INTO `sys_oauth_client_details` VALUES ('ssoclient2', 'ssoclient2', NULL, 'ssoclient2', 'sso', 'password,refresh_token,authorization_code,client_credentials,implicit', 'http://localhost:8102/sso/login,http://127.0.0.1:8102/sso/login,http://client.sso-2.com/sso/login', '', 432000, 2592001, '{\"website\":\"http://localhost:8102/sso\",\"appName\":\"单点登录客户端2\"}', 'true', 0, 0,'admin','admin',current_date,current_date,0);
INSERT INTO `sys_oauth_client_details` VALUES ('test', 'test', NULL, 'test', 'server', 'password,refresh_token', NULL, '', NULL, NULL, NULL, '', 0, 0,'admin','admin',current_date,current_date,0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_user` varchar(64) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT  CURRENT_TIMESTAMP '创建时间',
  `update_user` varchar(64) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP '更新时间',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `del_flag` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志',
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
  `create_user` varchar(64) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT  CURRENT_TIMESTAMP '创建时间',
  `update_user` varchar(64) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志',
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
  `del_flag` char(1) DEFAULT '0',
  `version` varchar(10) DEFAULT '0',
  `create_user` varchar(255) DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP ,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `tenant_id` int(11) DEFAULT '0' COMMENT '所属租户',
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
  `route_name` varchar(128) DEFAULT NULL,
  `route_id` varchar(128) DEFAULT NULL,
  `predicates` json DEFAULT NULL COMMENT '断言',
  `filters` json DEFAULT NULL COMMENT '过滤器',
  `uri` varchar(50) DEFAULT NULL,
  `order` int(2) DEFAULT '0' COMMENT '排序',
  `del_flag` char(1) DEFAULT '0',
  `version` varchar(10) DEFAULT '0',
  `create_user` varchar(255) DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP ,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `tenant_id` int(11) DEFAULT '0' COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='路由配置表';

-- ----------------------------
-- Records of sys_route_conf
-- ----------------------------
BEGIN;
INSERT INTO `sys_route_conf` VALUES (1, '认证中心', 'cleanarch-infrastructure-upms', '[{\"args\": {\"_genkey_0\": \"/auth/**\"}, \"name\": \"Path\"}]', '[]', 'lb://cleanarch-infrastructure-upms', 0, 0, 0,'admin','admin',current_date,current_date,0);
INSERT INTO `sys_route_conf` VALUES (2, '代码生成模块', 'cleanarch-infrastructure-codegen', '[{\"args\": {\"_genkey_0\": \"/codegen/**\"}, \"name\": \"Path\"}]', '[]', 'lb://cleanarch-infrastructure-codegen', 0, 0, 0,'admin','admin',current_date,current_date,0);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
                            `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
                            `type` varchar(100) DEFAULT NULL,
                            `description` varchar(100) DEFAULT NULL,
                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `remarks` varchar(255) DEFAULT NULL,
                            `system` char(1) DEFAULT '0',
                            `del_flag` char(1) DEFAULT '0',
                            `tenant_id` int(11) NOT NULL DEFAULT '0' COMMENT '所属租户',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `sys_dict_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES (1, 'log_type', '日志类型', '2019-03-19 11:06:44', '2019-03-19 11:06:44', '异常、正常', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (2, 'social_type', '社交登录', '2019-03-19 11:09:44', '2019-03-19 11:09:44', '微信、QQ', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (3, 'leave_status', '请假状态', '2019-03-19 11:09:44', '2019-03-19 11:09:44', '未提交、审批中、完成、驳回', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (4, 'job_type', '定时任务类型', '2019-03-19 11:22:21', '2019-03-19 11:22:21', 'quartz', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (5, 'job_status', '定时任务状态', '2019-03-19 11:24:57', '2019-03-19 11:24:57', '发布状态、运行状态', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (6, 'job_execute_status', '定时任务执行状态', '2019-03-19 11:26:15', '2019-03-19 11:26:15', '正常、异常', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (7, 'misfire_policy', '定时任务错失执行策略', '2019-03-19 11:27:19', '2019-03-19 11:27:19', '周期', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (8, 'gender', '性别', '2019-03-27 13:44:06', '2019-03-27 13:44:06', '微信用户性别', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (9, 'subscribe', '订阅状态', '2019-03-27 13:48:33', '2019-03-27 13:48:33', '公众号订阅状态', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (10, 'response_type', '回复', '2019-03-28 21:29:21', '2019-03-28 21:29:21', '微信消息是否已回复', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (11, 'param_type', '参数配置', '2019-04-29 18:20:47', '2019-04-29 18:20:47', '检索、原文、报表、安全、文档、消息、其他', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (12, 'status_type', '租户状态', '2019-05-15 16:31:08', '2019-05-15 16:31:08', '租户状态', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (13, 'dict_type', '字典类型', '2019-05-16 14:16:20', '2019-05-16 14:20:16', '系统类不能修改', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (14, 'channel_status', '支付渠道状态', '2019-05-30 16:14:43', '2019-05-30 16:14:43', '支付渠道状态（0-正常，1-冻结）', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (15, 'channel_id', '渠道编码ID', '2019-05-30 18:59:12', '2019-05-30 18:59:12', '不同的支付方式', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (16, 'order_status', '订单状态', '2019-06-27 08:17:40', '2019-06-27 08:17:40', '支付订单状态', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (17, 'grant_types', '授权类型', '2019-08-13 07:34:10', '2019-08-13 07:34:10', NULL, '1', '0', 1);
INSERT INTO `sys_dict` VALUES (18, 'style_type', '前端风格', '2020-02-07 03:49:28', '2020-02-07 03:50:40', '0-Avue 1-element', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (19, 'captcha_flag_types', '验证码开关', '2020-11-18 06:53:25', '2020-11-18 06:53:25', '是否校验验证码', '1', '0', 1);
INSERT INTO `sys_dict` VALUES (20, 'enc_flag_types', '前端密码加密', '2020-11-18 06:54:44', '2020-11-18 06:54:44', '前端密码是否加密传输', '1', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
                                 `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
                                 `dict_id` int(11) NOT NULL,
                                 `value` varchar(100) DEFAULT NULL,
                                 `label` varchar(100) DEFAULT NULL,
                                 `type` varchar(100) DEFAULT NULL,
                                 `description` varchar(100) DEFAULT NULL,
                                 `sort` int(10) NOT NULL DEFAULT '0' COMMENT '排序（升序）',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `remarks` varchar(255) DEFAULT NULL,
                                 `del_flag` char(1) DEFAULT '0',
                                 `tenant_id` int(11) NOT NULL DEFAULT '0' COMMENT '所属租户',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `sys_dict_value` (`value`) USING BTREE,
                                 KEY `sys_dict_label` (`label`) USING BTREE,
                                 KEY `sys_dict_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='字典项';

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_item` VALUES (1, 1, '9', '异常', 'log_type', '日志异常', 1, '2019-03-19 11:08:59', '2019-03-25 12:49:13', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (2, 1, '0', '正常', 'log_type', '日志正常', 0, '2019-03-19 11:09:17', '2019-03-25 12:49:18', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (3, 2, 'WX', '微信', 'social_type', '微信登录', 0, '2019-03-19 11:10:02', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (4, 2, 'QQ', 'QQ', 'social_type', 'QQ登录', 1, '2019-03-19 11:10:14', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (5, 3, '0', '未提交', 'leave_status', '未提交', 0, '2019-03-19 11:18:34', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (6, 3, '1', '审批中', 'leave_status', '审批中', 1, '2019-03-19 11:18:45', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (7, 3, '2', '完成', 'leave_status', '完成', 2, '2019-03-19 11:19:02', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (8, 3, '9', '驳回', 'leave_status', '驳回', 9, '2019-03-19 11:19:20', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (9, 4, '1', 'java类', 'job_type', 'java类', 1, '2019-03-19 11:22:37', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (10, 4, '2', 'spring bean', 'job_type', 'spring bean容器实例', 2, '2019-03-19 11:23:05', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (11, 4, '9', '其他', 'job_type', '其他类型', 9, '2019-03-19 11:23:31', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (12, 4, '3', 'Rest 调用', 'job_type', 'Rest 调用', 3, '2019-03-19 11:23:57', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (13, 4, '4', 'jar', 'job_type', 'jar类型', 4, '2019-03-19 11:24:20', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (14, 5, '1', '未发布', 'job_status', '未发布', 1, '2019-03-19 11:25:18', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (15, 5, '2', '运行中', 'job_status', '运行中', 2, '2019-03-19 11:25:31', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (16, 5, '3', '暂停', 'job_status', '暂停', 3, '2019-03-19 11:25:42', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (17, 6, '0', '正常', 'job_execute_status', '正常', 0, '2019-03-19 11:26:27', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (18, 6, '1', '异常', 'job_execute_status', '异常', 1, '2019-03-19 11:26:41', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (19, 7, '1', '错失周期立即执行', 'misfire_policy', '错失周期立即执行', 1, '2019-03-19 11:27:45', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (20, 7, '2', '错失周期执行一次', 'misfire_policy', '错失周期执行一次', 2, '2019-03-19 11:27:57', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (21, 7, '3', '下周期执行', 'misfire_policy', '下周期执行', 3, '2019-03-19 11:28:08', '2019-03-25 12:49:36', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (22, 8, '1', '男', 'gender', '微信-男', 0, '2019-03-27 13:45:13', '2019-03-27 13:45:13', '微信-男', '0', 1);
INSERT INTO `sys_dict_item` VALUES (23, 8, '2', '女', 'gender', '女-微信', 1, '2019-03-27 13:45:34', '2019-03-27 13:45:34', '女-微信', '0', 1);
INSERT INTO `sys_dict_item` VALUES (24, 8, '0', '未知', 'gender', 'x性别未知', 3, '2019-03-27 13:45:57', '2019-03-27 13:45:57', 'x性别未知', '0', 1);
INSERT INTO `sys_dict_item` VALUES (25, 9, '0', '未关注', 'subscribe', '公众号-未关注', 0, '2019-03-27 13:49:07', '2019-03-27 13:49:07', '公众号-未关注', '0', 1);
INSERT INTO `sys_dict_item` VALUES (26, 9, '1', '已关注', 'subscribe', '公众号-已关注', 1, '2019-03-27 13:49:26', '2019-03-27 13:49:26', '公众号-已关注', '0', 1);
INSERT INTO `sys_dict_item` VALUES (27, 10, '0', '未回复', 'response_type', '微信消息-未回复', 0, '2019-03-28 21:29:47', '2019-03-28 21:29:47', '微信消息-未回复', '0', 1);
INSERT INTO `sys_dict_item` VALUES (28, 10, '1', '已回复', 'response_type', '微信消息-已回复', 1, '2019-03-28 21:30:08', '2019-03-28 21:30:08', '微信消息-已回复', '0', 1);
INSERT INTO `sys_dict_item` VALUES (29, 11, '1', '检索', 'param_type', '检索', 0, '2019-04-29 18:22:17', '2019-04-29 18:22:17', '检索', '0', 1);
INSERT INTO `sys_dict_item` VALUES (30, 11, '2', '原文', 'param_type', '原文', 0, '2019-04-29 18:22:27', '2019-04-29 18:22:27', '原文', '0', 1);
INSERT INTO `sys_dict_item` VALUES (31, 11, '3', '报表', 'param_type', '报表', 0, '2019-04-29 18:22:36', '2019-04-29 18:22:36', '报表', '0', 1);
INSERT INTO `sys_dict_item` VALUES (32, 11, '4', '安全', 'param_type', '安全', 0, '2019-04-29 18:22:46', '2019-04-29 18:22:46', '安全', '0', 1);
INSERT INTO `sys_dict_item` VALUES (33, 11, '5', '文档', 'param_type', '文档', 0, '2019-04-29 18:22:56', '2019-04-29 18:22:56', '文档', '0', 1);
INSERT INTO `sys_dict_item` VALUES (34, 11, '6', '消息', 'param_type', '消息', 0, '2019-04-29 18:23:05', '2019-04-29 18:23:05', '消息', '0', 1);
INSERT INTO `sys_dict_item` VALUES (35, 11, '9', '其他', 'param_type', '其他', 0, '2019-04-29 18:23:16', '2019-04-29 18:23:16', '其他', '0', 1);
INSERT INTO `sys_dict_item` VALUES (36, 11, '0', '默认', 'param_type', '默认', 0, '2019-04-29 18:23:30', '2019-04-29 18:23:30', '默认', '0', 1);
INSERT INTO `sys_dict_item` VALUES (37, 12, '0', '正常', 'status_type', '状态正常', 0, '2019-05-15 16:31:34', '2019-05-16 22:30:46', '状态正常', '0', 1);
INSERT INTO `sys_dict_item` VALUES (38, 12, '9', '冻结', 'status_type', '状态冻结', 1, '2019-05-15 16:31:56', '2019-05-16 22:30:50', '状态冻结', '0', 1);
INSERT INTO `sys_dict_item` VALUES (39, 13, '1', '系统类', 'dict_type', '系统类字典', 0, '2019-05-16 14:20:40', '2019-05-16 14:20:40', '不能修改删除', '0', 1);
INSERT INTO `sys_dict_item` VALUES (40, 13, '0', '业务类', 'dict_type', '业务类字典', 0, '2019-05-16 14:20:59', '2019-05-16 14:20:59', '可以修改', '0', 1);
INSERT INTO `sys_dict_item` VALUES (41, 14, '0', '正常', 'channel_status', '支付渠道状态正常', 0, '2019-05-30 16:16:51', '2019-05-30 16:16:51', NULL, '0', 1);
INSERT INTO `sys_dict_item` VALUES (42, 14, '1', '冻结', 'channel_status', '支付渠道冻结', 0, '2019-05-30 16:17:08', '2019-05-30 16:17:08', NULL, '0', 1);
INSERT INTO `sys_dict_item` VALUES (43, 15, 'ALIPAY_WAP', '支付宝wap支付', 'channel_id', '支付宝扫码支付', 0, '2019-05-30 19:03:16', '2019-06-18 13:51:42', '支付宝wap支付', '0', 1);
INSERT INTO `sys_dict_item` VALUES (44, 15, 'WEIXIN_MP', '微信公众号支付', 'channel_id', '微信公众号支付', 1, '2019-05-30 19:08:08', '2019-06-18 13:51:53', '微信公众号支付', '0', 1);
INSERT INTO `sys_dict_item` VALUES (45, 16, '1', '支付成功', 'order_status', '支付成功', 1, '2019-06-27 08:18:26', '2019-06-27 08:18:26', '订单支付成功', '0', 1);
INSERT INTO `sys_dict_item` VALUES (46, 16, '2', '支付完成', 'order_status', '订单支付完成', 2, '2019-06-27 08:18:44', '2019-06-27 08:18:44', '订单支付完成', '0', 1);
INSERT INTO `sys_dict_item` VALUES (47, 16, '0', '待支付', 'order_status', '订单待支付', 0, '2019-06-27 08:19:02', '2019-06-27 08:19:02', '订单待支付', '0', 1);
INSERT INTO `sys_dict_item` VALUES (48, 16, '-1', '支付失败', 'order_status', '订单支付失败', 3, '2019-06-27 08:19:37', '2019-06-27 08:19:37', '订单支付失败', '0', 1);
INSERT INTO `sys_dict_item` VALUES (49, 2, 'GITEE', '码云', 'social_type', '码云', 2, '2019-06-28 09:59:12', '2019-06-28 09:59:12', '码云', '0', 1);
INSERT INTO `sys_dict_item` VALUES (50, 2, 'OSC', '开源中国', 'social_type', '开源中国登录', 0, '2019-06-28 10:04:32', '2019-06-28 10:04:32', 'http://gitee.huaxiadaowei.com/#/authredirect', '0', 1);
INSERT INTO `sys_dict_item` VALUES (51, 17, 'password', '密码模式', 'grant_types', '支持oauth密码模式', 0, '2019-08-13 07:35:28', '2019-08-13 07:35:28', NULL, '0', 1);
INSERT INTO `sys_dict_item` VALUES (52, 17, 'authorization_code', '授权码模式', 'grant_types', 'oauth2 授权码模式', 1, '2019-08-13 07:36:07', '2019-08-13 07:36:07', NULL, '0', 1);
INSERT INTO `sys_dict_item` VALUES (53, 17, 'client_credentials', '客户端模式', 'grant_types', 'oauth2 客户端模式', 2, '2019-08-13 07:36:30', '2019-08-13 07:36:30', NULL, '0', 1);
INSERT INTO `sys_dict_item` VALUES (54, 17, 'refresh_token', '刷新模式', 'grant_types', 'oauth2 刷新token', 3, '2019-08-13 07:36:54', '2019-08-13 07:36:54', NULL, '0', 1);
INSERT INTO `sys_dict_item` VALUES (55, 17, 'implicit', '简化模式', 'grant_types', 'oauth2 简化模式', 4, '2019-08-13 07:39:32', '2019-08-13 07:39:32', NULL, '0', 1);
INSERT INTO `sys_dict_item` VALUES (56, 18, '0', 'Avue', 'style_type', 'Avue风格', 0, '2020-02-07 03:52:52', '2020-02-07 03:52:52', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (57, 18, '1', 'element', 'style_type', 'element-ui', 1, '2020-02-07 03:53:12', '2020-02-07 03:53:12', '', '0', 1);
INSERT INTO `sys_dict_item` VALUES (58, 19, '0', '关', 'captcha_flag_types', '不校验验证码', 0, '2020-11-18 06:53:58', '2020-11-18 06:53:58', '不校验验证码 -0', '0', 1);
INSERT INTO `sys_dict_item` VALUES (59, 19, '1', '开', 'captcha_flag_types', '校验验证码', 1, '2020-11-18 06:54:15', '2020-11-18 06:54:15', '不校验验证码-1', '0', 1);
INSERT INTO `sys_dict_item` VALUES (60, 20, '0', '否', 'enc_flag_types', '不加密', 0, '2020-11-18 06:55:31', '2020-11-18 06:55:31', '不加密-0', '0', 1);
INSERT INTO `sys_dict_item` VALUES (61, 20, '1', '是', 'enc_flag_types', '加密', 1, '2020-11-18 06:55:51', '2020-11-18 06:55:51', '加密-1', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
                            `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
                            `file_name` varchar(100) DEFAULT NULL,
                            `bucket_name` varchar(200) DEFAULT NULL,
                            `original` varchar(100) DEFAULT NULL,
                            `type` varchar(50) DEFAULT NULL,
                            `file_size` bigint(50) DEFAULT NULL COMMENT '文件大小',
                            `del_flag` char(1) DEFAULT '0',
                            `version` varchar(10) DEFAULT '0',
                            `create_user` varchar(255) DEFAULT NULL,
                            `update_user` varchar(255) DEFAULT NULL,
                            `create_date` datetime DEFAULT CURRENT_TIMESTAMP ,
                            `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                            `tenant_id` int(11) DEFAULT '0' COMMENT '所属租户',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件管理表';

SET FOREIGN_KEY_CHECKS = 1;
