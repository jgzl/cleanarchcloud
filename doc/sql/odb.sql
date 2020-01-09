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

drop database if exists odb;
drop database if exists odb0;
drop database if exists odb1;
drop database if exists odb2;
drop database if exists odb3;
drop database if exists odb4;
drop database if exists odb5;
drop database if exists odb6;
drop database if exists odb7;
create database odb charset utf8mb4;
create database odb0 charset utf8mb4;
create database odb1 charset utf8mb4;
create database odb2 charset utf8mb4;
create database odb3 charset utf8mb4;
create database odb4 charset utf8mb4;
create database odb5 charset utf8mb4;
create database odb6 charset utf8mb4;
create database odb7 charset utf8mb4;

USE odb7;
CREATE TABLE o_order0 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order1 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE `o_dict` (
	`id` BIGINT ( 20 ) NOT NULL,
	`shop_id` BIGINT ( 20 ) NULL DEFAULT NULL,
	`key` VARCHAR ( 200 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
CREATE TABLE o_order_item0 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order_item1 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
PRIMARY KEY ( id )
);
USE odb6;
CREATE TABLE o_order0 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order1 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE `o_dict` (
	`id` BIGINT ( 20 ) NOT NULL,
	`shop_id` BIGINT ( 20 ) NULL DEFAULT NULL,
	`key` VARCHAR ( 200 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
CREATE TABLE o_order_item0 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order_item1 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
PRIMARY KEY ( id )
);
USE odb5;
CREATE TABLE o_order0 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order1 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE `o_dict` (
	`id` BIGINT ( 20 ) NOT NULL,
	`shop_id` BIGINT ( 20 ) NULL DEFAULT NULL,
	`key` VARCHAR ( 200 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
CREATE TABLE o_order_item0 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order_item1 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
PRIMARY KEY ( id )
);
USE odb4;
CREATE TABLE o_order0 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order1 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE `o_dict` (
	`id` BIGINT ( 20 ) NOT NULL,
	`shop_id` BIGINT ( 20 ) NULL DEFAULT NULL,
	`key` VARCHAR ( 200 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
CREATE TABLE o_order_item0 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order_item1 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
PRIMARY KEY ( id )
);
USE odb3;
CREATE TABLE o_order0 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order1 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE `o_dict` (
	`id` BIGINT ( 20 ) NOT NULL,
	`shop_id` BIGINT ( 20 ) NULL DEFAULT NULL,
	`key` VARCHAR ( 200 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
CREATE TABLE o_order_item0 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order_item1 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
PRIMARY KEY ( id )
);
USE odb2;
CREATE TABLE o_order0 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order1 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE `o_dict` (
	`id` BIGINT ( 20 ) NOT NULL,
	`shop_id` BIGINT ( 20 ) NULL DEFAULT NULL,
	`key` VARCHAR ( 200 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
CREATE TABLE o_order_item0 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order_item1 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
PRIMARY KEY ( id )
);
USE odb1;
CREATE TABLE o_order0 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order1 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE `o_dict` (
	`id` BIGINT ( 20 ) NOT NULL,
	`shop_id` BIGINT ( 20 ) NULL DEFAULT NULL,
	`key` VARCHAR ( 200 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
CREATE TABLE o_order_item0 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order_item1 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
PRIMARY KEY ( id )
);
USE odb0;
CREATE TABLE o_order0 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order1 (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE `o_dict` (
	`id` BIGINT ( 20 ) NOT NULL,
	`shop_id` BIGINT ( 20 ) NULL DEFAULT NULL,
	`key` VARCHAR ( 200 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
CREATE TABLE o_order_item0 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
	PRIMARY KEY ( id )
);
CREATE TABLE o_order_item1 (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
PRIMARY KEY ( id )
);
USE odb;
CREATE TABLE o_order (
	id BIGINT AUTO_INCREMENT,
	shop_id BIGINT NOT NULL,
	CODE VARCHAR ( 50 ),
	create_time TIMESTAMP ( 0 ),
	PRIMARY KEY ( id )
);
CREATE TABLE `o_dict` (
	`id` BIGINT ( 20 ) NOT NULL,
	`shop_id` BIGINT ( 20 ) NULL DEFAULT NULL,
	`key` VARCHAR ( 200 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
CREATE TABLE o_order_item (
	id BIGINT AUTO_INCREMENT,
	order_id BIGINT,
	shop_id BIGINT NOT NULL,
	NAME VARCHAR ( 50 ),
	PRIMARY KEY ( id )
);