package com.dlihaifeng.conversion.platform.application.template.service.impl;

import org.apache.dubbo.config.annotation.Service;

import com.dlihaifeng.conversion.platform.application.template.service.EchoService;

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
