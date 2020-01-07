package com.gitee.common.data.mybatis;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gitee.common.security.util.SecurityUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 */
@Slf4j
public class PlatformMetaObjectHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    log.info("start insert fill ....");
    //版本号3.0.6以及之前的版本
    this.setFieldValByName("createdBy",SecurityUtils.getAuthentication()==null?-1L:SecurityUtils.getUser()==null?-1L:SecurityUtils.getUser().getId(), metaObject);
    this.setFieldValByName("createdTime", LocalDateTime.now(), metaObject);
    this.setFieldValByName("updatedBy", SecurityUtils.getAuthentication()==null?-1L:SecurityUtils.getUser()==null?-1L:SecurityUtils.getUser().getId(), metaObject);
    this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    log.info("start update fill ....");
    this.setFieldValByName("updatedBy", SecurityUtils.getAuthentication()==null?-1L:SecurityUtils.getUser()==null?-1L:SecurityUtils.getUser().getId(), metaObject);
    this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
  }
}
