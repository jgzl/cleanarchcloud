package com.github.jgzl.infra.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jgzl.infra.upms.dataobject.SysSocialUser;
import com.github.jgzl.infra.upms.dataobject.SysSocialUserAuth;
import com.github.jgzl.infra.upms.mapper.SysSocialUserAuthMapper;
import com.github.jgzl.infra.upms.mapper.SysSocialUserMapper;
import com.github.jgzl.infra.upms.service.SysSocialUserAuthService;
import com.github.jgzl.infra.upms.service.SysSocialUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 文件管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysSocialUserAuthServiceImpl extends ServiceImpl<SysSocialUserAuthMapper, SysSocialUserAuth> implements SysSocialUserAuthService {

}
