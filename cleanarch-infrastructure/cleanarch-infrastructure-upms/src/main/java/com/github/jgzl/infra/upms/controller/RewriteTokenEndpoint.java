package com.github.jgzl.infra.upms.controller;

import com.github.jgzl.common.core.exception.CheckedException;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.core.util.StringUtils;
import com.github.jgzl.common.data.TenantEnvironment;
import com.github.jgzl.infra.upms.domain.dto.ChangePasswordDTO;
import com.github.jgzl.infra.upms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * 重写响应端点。主要是为了封装响应结果
 *
 * @author Levin
 */
@Slf4j
@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class RewriteTokenEndpoint {

    private final TenantEnvironment tenantEnvironment;
    private final UserService userService;
	private final TokenEndpoint tokenEndpoint;

	@ResponseBody
	@RequestMapping(value = "/token", method = {RequestMethod.GET, RequestMethod.POST})
	public Result<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters, HttpServletRequest request) throws HttpRequestMethodNotSupportedException {
		final Result<OAuth2AccessToken> success = Result.success(tokenEndpoint.postAccessToken(principal, parameters).getBody());
		return success;
	}
	/**
	 * 获取当前用户信息
	 * @param principal
	 * @return
	 */
    @ResponseBody
    @GetMapping("/info")
    public Result<Object> userInfo(Principal principal) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        return Result.success(oAuth2Authentication.getPrincipal());
    }

	/**
	 * 获取当前用户信息
	 * @param principal
	 * @return
	 */
    @ResponseBody
    @GetMapping("/users")
    public Principal users(Principal principal) {
        return principal;
    }

	/**
	 * 修改密码
	 * @param dto
	 * @return
	 */
    @ResponseBody
    @PutMapping("/change_password")
    public Result<Void> changePassword(@Validated @RequestBody ChangePasswordDTO dto) {
        if (!StringUtils.equals(dto.getPassword(), dto.getConfirmPassword())) {
            throw CheckedException.badRequest("新密码与确认密码不一致");
        }
        final Long userId = tenantEnvironment.userId();
        this.userService.changePassword(userId, dto.getOriginalPassword(), dto.getPassword());
        return Result.success();
    }


}
