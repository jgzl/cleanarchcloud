package com.gitee.common.security.vo;

import java.io.Serializable;
import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.gitee.common.security.dataobject.SysOauthClientDetailsDO;
import com.gitee.common.security.validate.Add;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 客户端vo
 *
 * @author lihaifeng
 * 2019/7/5 15:12
 */
@Data
public class SysOauthClientDetailsVO implements Serializable {

    private String clientId;

    @NotNull(message = "客户端名称不能为空")
    @Size(max = 50, message = "客户端名称最多50个字符")
    private String appName;

    private Collection<String> resourceIds;

    @NotNull(message = "客户端密钥不能为空", groups = Add.class)
    private String clientSecret;

    @NotNull(message = "权限范围不能为空")
    @Size(min = 1, max = 5, message = "最少1个权限, 最多5个权限")
    private Collection<String> scope;

    @NotNull(message = "授权类型不能为空")
    @Size(min = 1, max = 5, message = "最少1个授权类型, 最多5个授权类型")
    private Collection<String> authorizedGrantTypes;

    @NotNull(message = "重定向地址不能为空")
    @Pattern(regexp = "^(https?|ftp)://([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\\.)*[a-zA-Z0-9-]+\\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(/($|[a-zA-Z0-9.,?'\\\\+&%$#=~_-]+))*$",
            message = "重定向地址不合法")
    private String webServerRedirectUri;

    private Collection<String> authorities;

    @NotNull(message = "是否自动认证不能为空")
    private Boolean autoapprove;

	private String additionalInformation;

    public SysOauthClientDetailsVO(){}

    public SysOauthClientDetailsVO(final SysOauthClientDetailsDO client) {
        this.setClientId(client.getClientId());
        this.setAppName(client.getAppName());
        this.setResourceIds(StrUtil.splitTrim(client.getResourceIds(), StrUtil.COMMA));
        this.setClientSecret(client.getClientSecret());
        this.setScope(StrUtil.splitTrim(client.getScope(), StrUtil.COMMA));
        this.setAuthorizedGrantTypes(StrUtil.splitTrim(client.getAuthorizedGrantTypes(), StrUtil.COMMA));
        this.setWebServerRedirectUri(client.getWebServerRedirectUri());
        this.setAuthorities(StrUtil.splitTrim(client.getAuthorities(), StrUtil.COMMA));
        this.setAutoapprove(Boolean.valueOf(client.getAutoapprove()));
        this.setAdditionalInformation(client.getAdditionalInformation());
    }
}
