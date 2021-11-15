package com.github.jgzl.infra.upms.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.SocialUserAuth;
import com.github.jgzl.infra.upms.repository.SysSocialUserAuthMapper;
import com.github.jgzl.infra.upms.service.SysSocialUserAuthService;
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
public class SysSocialUserAuthServiceImpl extends ServiceImpl<SysSocialUserAuthMapper, SocialUserAuth> implements SysSocialUserAuthService {
}
