package com.gitee.common.security.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.gitee.common.security.vo.SsoOauthClientDetailsVO;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 客户端实体类
 *
 * @author lihaifeng
 * 2019/7/5 14:48
 */
@Data
@Accessors(chain = true)
@TableName("sso_oauth_client_details")
public class SsoOauthClientDetailsDAO extends Model<SsoOauthClientDetailsDAO> {

    private String clientId;

    private String appName;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;

    public SsoOauthClientDetailsDAO(){}
    public SsoOauthClientDetailsDAO(final SsoOauthClientDetailsVO vo) {
        this.setClientId(vo.getClientId());
        this.setAppName(vo.getAppName());
        this.setResourceIds(CollUtil.join(vo.getResourceIds(), StrUtil.COMMA));
        this.setClientSecret(vo.getClientSecret());
        this.setScope(CollUtil.join(vo.getScope(), StrUtil.COMMA));
        this.setAuthorizedGrantTypes(CollUtil.join(vo.getAuthorizedGrantTypes(), StrUtil.COMMA));
        this.setWebServerRedirectUri(vo.getWebServerRedirectUri());
        this.setAuthorities(CollUtil.join(vo.getAuthorities(), StrUtil.COMMA));
        this.setAutoapprove(String.valueOf(vo.getAutoapprove()));
    }
}
