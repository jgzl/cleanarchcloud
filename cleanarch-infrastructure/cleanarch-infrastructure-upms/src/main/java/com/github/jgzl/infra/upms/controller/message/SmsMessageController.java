package com.github.jgzl.infra.upms.controller.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lihaifeng
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/sms_messages")
public class SmsMessageController {


}
