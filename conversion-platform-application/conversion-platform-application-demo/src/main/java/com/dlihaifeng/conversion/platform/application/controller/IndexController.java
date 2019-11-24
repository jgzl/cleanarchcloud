/*
 * Copyright (c) 2019-2019
 */

package com.dlihaifeng.conversion.platform.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 */
@Slf4j
@Controller
@ResponseBody
public class IndexController {

  @Autowired
  private Environment environment;

  @RequestMapping("ping")
  public String ping() {
    log.info("ping_url:{}", environment.getProperty("server.port"));
    return environment.getProperty("server.port");
  }
}
