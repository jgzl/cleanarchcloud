package com.github.jgzl.infra.upms.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.SocialUser;
import com.github.jgzl.infra.upms.repository.SysSocialUserMapper;
import com.github.jgzl.infra.upms.service.SysSocialUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * 文件管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysSocialUserServiceImpl extends ServiceImpl<SysSocialUserMapper, SocialUser> implements SysSocialUserService {
	@Override
	public List<SocialUser> findSocialUserByUserIdAndSource(String userId, String source) {
		return this.baseMapper.findSocialUserByUserIdAndSource(userId,source);
	}
}
