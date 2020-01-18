package com.gitee.application.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lihaifeng
 */
@Controller
public class IndexController {
  @GetMapping(value = {"/","/index"})
  public String redirectToMember(){
    return "member/list";
  }
}
