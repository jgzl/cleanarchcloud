package com.github.jgzl.application.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.jgzl.application.model.vo.WelcomeVo;
import com.github.jgzl.common.annotation.LogRecord;
import com.github.jgzl.common.enums.OperateModule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	@LogRecord(operateContent = "abc", operateModule = OperateModule.BUSINESS1)
	public String welcomeByModelMap(ModelMap mm) {
		WelcomeVo welcomeVo = WelcomeVo.builder().userName("李海峰")
				.urlName("/index.html")
				.urlDescription("首页").build();
		mm.addAttribute("welcomeVo", welcomeVo);
		return "welcome";
	}

	@GetMapping("mv")
	@ApiOperation(value = "跳转welcome页2")
	public ModelAndView welcomeByModelAndView(ModelAndView mv) {
		List<Map<String,Object>> list = new ArrayList<>();
		WelcomeVo welcomeVo = WelcomeVo.builder().userName("李海峰")
				.urlName("/index.html")
				.urlDescription("首页").build();
		mv.addObject("welcomeVo", welcomeVo);
		mv.addObject("today", new Date());
		mv.addObject("testStr1",null);
		mv.addObject("testStr2","testValue");
		mv.setViewName("welcome");
		return mv;
	}
}
