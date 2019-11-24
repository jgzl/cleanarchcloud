/*
 * Copyright (c) 2019-2019
 */

package com.dlihaifeng.conversion.platform.application.controller;

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

  @RequestMapping("ping")
  public String ping() {
    log.info("ping_url:{}", "127.0.0.1");
    return "pong";
  }
}
