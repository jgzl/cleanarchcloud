package com.gitee.application.auth.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gitee.application.auth.bussiness.dto.UserDTO;
import com.gitee.application.auth.bussiness.service.PlatformSsoUserService;
import com.gitee.common.core.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("token")
@Api(value = "token端点", tags = "token端点")
public class PlatformTokenController {

  private final ClientDetailsService clientDetailsService;

  private final PlatformSsoUserService platformSsoUserService;

  private final TokenStore tokenStore;

  /**
   * 认证页面
   *
   * @param modelAndView
   * @param error        表单登录失败处理回调的错误信息
   * @return ModelAndView
   */
  @GetMapping("/login")
  @ApiOperation(value = "登录接口", notes = "跳转登录页面")
  public ModelAndView require(ModelAndView modelAndView, @RequestParam(required = false) String error) {
    modelAndView.setViewName("ftl/login");
    modelAndView.addObject("error", error);
    return modelAndView;
  }

  @GetMapping("/create")
  @ApiOperation(value = "创建新用户", notes = "创建新用户")
  public String createUser(UserDTO userDTO) {
    platformSsoUserService.saveUser(userDTO);
    return "success";
  }

  /**
   * 确认授权页面
   *
   * @param request
   * @param session
   * @param modelAndView
   * @return
   */
  @GetMapping("/confirm_access")
  public ModelAndView confirm(HttpServletRequest request, HttpSession session, ModelAndView modelAndView) {
    Map<String, Object> scopeList = (Map<String, Object>) request.getAttribute("scopes");
    modelAndView.addObject("scopeList", scopeList.keySet());

    Object auth = session.getAttribute("authorizationRequest");
    if (auth != null) {
      AuthorizationRequest authorizationRequest = (AuthorizationRequest) auth;
      ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());
      modelAndView.addObject("app", clientDetails.getAdditionalInformation());
      modelAndView.addObject("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    modelAndView.setViewName("ftl/confirm");
    return modelAndView;
  }

  /**
   * 退出token
   *
   * @param authHeader Authorization
   */
  @DeleteMapping("/logout")
  public R logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
    if (StrUtil.isBlank(authHeader)) {
      return R.ok(Boolean.FALSE, "退出失败，token 为空");
    }

    String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
    OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
    if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
      return R.ok(Boolean.TRUE, "退出失败，token 无效");
    }

    OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(accessToken);
    // 清空用户信息
    /*cacheManager.getCache(CacheConstants.USER_DETAILS)
        .evict(auth2Authentication.getName());*/

    // 清空access token
    tokenStore.removeAccessToken(accessToken);

    // 清空 refresh token
    OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
    tokenStore.removeRefreshToken(refreshToken);
    return R.ok(Boolean.TRUE);
  }

  /**
   * 令牌管理调用
   *
   * @param token token
   * @return
   */
  @DeleteMapping("/{token}")
  public R<Boolean> delToken(@PathVariable("token") String token) {
    OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
    tokenStore.removeAccessToken(oAuth2AccessToken);
    return new R<>();
  }
}
