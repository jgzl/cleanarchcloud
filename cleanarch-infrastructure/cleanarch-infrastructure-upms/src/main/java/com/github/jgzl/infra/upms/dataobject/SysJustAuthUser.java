package com.github.jgzl.infra.upms.dataobject;
import lombok.Data;
import me.zhyd.oauth.model.AuthUser;
import java.io.Serializable;

/**
 * 第三方登录用户
 */
@Data
public class SysJustAuthUser extends AuthUser implements Serializable {

}
