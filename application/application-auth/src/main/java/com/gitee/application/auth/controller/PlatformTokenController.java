/*
 *    Copyright [2020] [lihaifeng,xuhang]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gitee.application.auth.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.cache.CacheManager;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gitee.application.auth.bussiness.service.PlatformSsoUserService;
import com.gitee.common.core.constant.CacheConstants;
import com.gitee.common.core.util.Result;
import com.gitee.common.upms.dto.UserDTO;

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

  private final CacheManager cacheManager;

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

  @PostMapping("/create")
  @ApiOperation(value = "创建新用户", notes = "创建新用户",httpMethod = "POST")
  public String createUser(@RequestBody UserDTO userDTO) {
    platformSsoUserService.saveUser(userDTO);
    return "success";
  }

  @GetMapping("/baidu")
  @ApiOperation(value = "重定向到百度", notes = "重定向网址",httpMethod = "GET")
  public ModelAndView redirectToBaidu(ModelAndView modelAndView){
    modelAndView.setViewName("redirect:https://www.baidu.com");
    return modelAndView;
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
  public Result logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
    if (StrUtil.isBlank(authHeader)) {
      return Result.ok(Boolean.FALSE, "退出失败，token 为空");
    }

    String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
    OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
    if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
      return Result.ok(Boolean.TRUE, "退出失败，token 无效");
    }

    OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(accessToken);
    // 清空用户信息
    cacheManager.getCache(CacheConstants.USER_DETAILS)
        .evict(auth2Authentication.getName());

    // 清空access token
    tokenStore.removeAccessToken(accessToken);

    // 清空 refresh token
    OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
    tokenStore.removeRefreshToken(refreshToken);
    return Result.ok(Boolean.TRUE);
  }

  /**
   * 令牌管理调用
   *
   * @param token token
   * @return
   */
  @DeleteMapping("/{token}")
  public Result<Boolean> delToken(@PathVariable("token") String token) {
    OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
    tokenStore.removeAccessToken(oAuth2AccessToken);
    return new Result<>();
  }
}
