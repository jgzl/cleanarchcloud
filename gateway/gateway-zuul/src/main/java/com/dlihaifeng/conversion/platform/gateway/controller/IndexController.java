package com.dlihaifeng.conversion.platform.gateway.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dlihaifeng.conversion.platform.gateway.feign.service.DemoRemoteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 */
@Api(value = "主页")
@Slf4j
@RestController
public class IndexController {
  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private DemoRemoteService demoRemoteService;

  /**
   * http://localhost:8081/demo
   * @return
   */
  @ApiOperation(value = "通过restTemplate的restful方式调用demo服务")
  @RequestMapping("/demo-rest")
  public String demo() {
    String result = restTemplate.getForObject("http://conversion-platform-application-demo/ping", String.class);
    return result;
  }

  @ApiOperation(value = "心跳存活接口")
  @RequestMapping("ping")
  public String ping(HttpServletRequest request) {
    log.info("ping_url:{}", request.getRequestURI());
    log.info("ping_url:{}", request.getRequestURL());
    return "pong";
  }

  /**
   * 使用openfeign调用demoservice相关服务
   * @param request
   * @return
   */
  @ApiOperation(value = "通过OpenFeign方式调用demo服务")
  @RequestMapping("test")
  public String test(HttpServletRequest request) {
    log.info("demo service port:{}", demoRemoteService.getServerPort());
    return "demo service port:" + demoRemoteService.getServerPort();
  }
}
