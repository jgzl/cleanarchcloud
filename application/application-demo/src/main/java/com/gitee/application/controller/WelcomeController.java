package com.gitee.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gitee.application.model.vo.WelcomeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lihaifeng
 */
@Controller
@RequestMapping("welcome")
@Api(value = "欢迎页面", tags = {"欢迎页面"})
public class WelcomeController {
  @GetMapping("mm")
  @ApiOperation(value = "跳转welcome页1")
  public String welcomeByModelMap(ModelMap mm) {
    WelcomeVO welcomeVO = WelcomeVO.builder().userName("李海峰")
        .urlName("/index.html")
        .urlDescription("首页").build();
    mm.addAttribute("welcomeVO", welcomeVO);
    return "welcome";
  }

  @GetMapping("mv")
  @ApiOperation(value = "跳转welcome页2")
  public ModelAndView welcomeByModelAndView(ModelAndView mv) {
    WelcomeVO welcomeVO = WelcomeVO.builder().userName("李海峰")
        .urlName("/index.html")
        .urlDescription("首页").build();
    mv.addObject("welcomeVO", welcomeVO);
    mv.setViewName("welcome");
    return mv;
  }
}
