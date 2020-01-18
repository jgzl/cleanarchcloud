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

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gitee.common.core.util.Result;
import com.gitee.common.security.util.SecurityUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lihaifeng
 */
@Controller
@Api("主页")
public class PlatformIndexController {
  @ApiOperation(value = "获取用户信息",notes = "通过安全工具类获取具体用户信息",httpMethod = "GET")
  @GetMapping("/user")
  @ResponseBody
  public Result user(Authentication authentication){
    return Result.ok(authentication);
  }

  @GetMapping({"/","index"})
  public ModelAndView index(Authentication authentication, ModelAndView modelAndView) {
    modelAndView.addObject("username",authentication.getName());
    modelAndView.setViewName("index");
    return modelAndView;
  }
}
