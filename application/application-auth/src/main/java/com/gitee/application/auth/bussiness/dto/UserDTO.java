package com.gitee.application.auth.bussiness.dto;

import com.gitee.application.auth.bussiness.domain.PlatformSsoUser;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
@ApiModel(value = "系统用户传输对象")
public class UserDTO extends PlatformSsoUser {
}
