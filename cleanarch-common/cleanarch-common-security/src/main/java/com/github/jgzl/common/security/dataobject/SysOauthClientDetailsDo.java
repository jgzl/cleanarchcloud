package com.github.jgzl.common.security.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.github.jgzl.common.security.vo.SysOauthClientDetailsVo;

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
@TableName("sys_oauth_client_details")
public class SysOauthClientDetailsDo extends Model<SysOauthClientDetailsDo> {

	@TableId(type = IdType.INPUT)
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

    public SysOauthClientDetailsDo(){}
    public SysOauthClientDetailsDo(final SysOauthClientDetailsVo vo) {
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
