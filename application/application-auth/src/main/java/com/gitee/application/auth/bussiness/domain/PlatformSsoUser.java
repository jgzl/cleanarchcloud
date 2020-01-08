package com.gitee.application.auth.bussiness.domain;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.common.core.sensitive.Sensitive;
import com.gitee.common.data.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
@ApiModel(value = "用户")
@TableName("platform_sso_user")
public class PlatformSsoUser extends BaseModel<PlatformSsoUser> {
  /** 用户id */
  @ApiModelProperty("主键id")
  @TableId(type = IdType.ID_WORKER)
  private Long id ;
  /** 用户姓名(账户) */
  @ApiModelProperty("用户姓名(账户)")
  private String username ;
  /** 用户昵称 */
  @ApiModelProperty("用户昵称")
  private String nickname ;
  /** 用户密码 */
  @Sensitive
  @ApiModelProperty("用户密码")
  private String password ;
  /** 手机号 */
  @Sensitive
  @ApiModelProperty("手机号")
  private String mobile ;
  /** 邮箱 */
  @Sensitive
  @ApiModelProperty("邮箱")
  private String email ;
  /** 登录次数 */
  @ApiModelProperty("登录次数")
  private Integer loginCount ;
  /** 登录错误次数 */
  @ApiModelProperty("登录错误次数")
  private Integer loginErrorCount ;
  /** 登录时间(最新) */
  @ApiModelProperty("登录时间(最新)")
  private LocalDateTime loginTime ;
  /** 账号状态;0-未锁定，1-已锁定，2-以离职（废弃） */
  @ApiModelProperty("账号状态;0-未锁定，1-已锁定，2-以离职（废弃）")
  private Integer loginStatus ;
  /** 头像;远程图片地址 */
  @ApiModelProperty("头像")
  private String avatar ;
}
