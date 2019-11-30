package com.dlihaifeng.conversion.platform.application.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dlihaifeng.conversion.platform.application.template.service.EchoService;

/**
 * @author lihaifeng
 */
@RestController
public class IndexController {

  @Reference
  private EchoService echoService;

  @GetMapping("/echo")
  public String echo() {
    String message = echoService.echo("李海峰");
    return message;
  }
}
