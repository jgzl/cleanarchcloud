package com.dlihaifeng.conversion.platform.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dlihaifeng.conversion.platform.application.model.vo.WelcomeVO;

/**
 * @author lihaifeng
 */
@Controller
public class WelcomeController {
  @GetMapping("welcome/mm")
  public String welcomeByModelMap(ModelMap mm) {
    WelcomeVO welcomeVO = WelcomeVO.builder().userName("李海峰")
        .urlName("/index.html")
        .urlDescription("首页").build();
    mm.addAttribute("welcomeVO", welcomeVO);
    return "welcome";
  }

  @GetMapping("welcome/mv")
  public ModelAndView welcomeByModelAndView(ModelAndView mv) {
    WelcomeVO welcomeVO = WelcomeVO.builder().userName("李海峰")
        .urlName("/index.html")
        .urlDescription("首页").build();
    mv.addObject("welcomeVO", welcomeVO);
    mv.setViewName("welcome");
    return mv;
  }
}
