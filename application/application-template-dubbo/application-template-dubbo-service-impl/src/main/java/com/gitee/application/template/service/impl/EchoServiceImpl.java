package com.gitee.application.template.service.impl;

import org.apache.dubbo.config.annotation.Service;

import com.gitee.application.template.service.EchoService;

/**
 * @author lihaifeng
 */
@Service
public class EchoServiceImpl implements EchoService {
  @Override
  public String echo(String message) {
    return "[echo] Hello, " + message;
  }
}
