package com.gitee.application.auth.bussiness.domain;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.common.data.base.BaseModel;

import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
@TableName("platform_sso_user")
public class PlatformSsoUser extends BaseModel<PlatformSsoUser> {
  /** 用户id */
  @TableId(type = IdType.ID_WORKER)
  private Long id ;
  /** 用户姓名(账户) */
  private String username ;
  /** 用户昵称 */
  private String nickname ;
  /** 用户密码 */
  private String password ;
  /** 手机号 */
  private String mobile ;
  /** 邮箱 */
  private String email ;
  /** 登录次数 */
  private Integer loginCount ;
  /** 登录错误次数 */
  private Integer loginErrorCount ;
  /** 登录时间(最新) */
  //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime loginTime ;
  /** 账号状态;0-未锁定，1-已锁定，2-以离职（废弃） */
  private Integer loginStatus ;
  /** 头像;远程图片地址 */
  private String avatar ;
}
