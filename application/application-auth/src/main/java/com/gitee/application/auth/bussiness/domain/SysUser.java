package com.gitee.application.auth.bussiness.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 *   `id` bigint(20) NOT NULL,
 *   `name` varchar(20) DEFAULT NULL,
 *   `username` varchar(20) NOT NULL,
 *   `password` varchar(128) NOT NULL,
 *   `tel` varchar(20) DEFAULT NULL,
 *   `gender` varchar(10) DEFAULT NULL,
 *   `createTime` datetime DEFAULT NULL,
 */
@Data
@TableName("sys_user")
public class SysUser {
  @TableId(type = IdType.ID_WORKER)
  private Long id;

  private String name;

  private String username;

  private String password;

  private String tel;

  private String gender;

  private Date createTime;
}
