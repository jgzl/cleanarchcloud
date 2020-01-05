package com.gitee.application.auth.bussiness.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author lihaifeng
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
