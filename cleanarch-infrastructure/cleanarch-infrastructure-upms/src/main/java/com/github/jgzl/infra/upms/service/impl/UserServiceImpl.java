package com.github.jgzl.infra.upms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.jgzl.common.core.exception.CheckedException;
import com.github.jgzl.common.data.mybatis.SuperServiceImpl;
import com.github.jgzl.common.data.mybatis.auth.DataScope;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.mybatis.conditions.query.LbqWrapper;
import com.github.jgzl.infra.upms.domain.dto.UserSaveDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.SocialUser;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.SocialUserAuth;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.User;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.UserRole;
import com.github.jgzl.infra.upms.domain.vo.UserResp;
import com.github.jgzl.infra.upms.repository.SysSocialUserAuthMapper;
import com.github.jgzl.infra.upms.repository.SysSocialUserMapper;
import com.github.jgzl.infra.upms.repository.UserMapper;
import com.github.jgzl.infra.upms.repository.UserRoleMapper;
import com.github.jgzl.infra.upms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Levin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {

    private static final String PHONE_REGEX = "^[1][0-9]{10}$";
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
	private final SysSocialUserMapper sysSocialUserMapper;
	private final SysSocialUserAuthMapper sysSocialUserAuthMapper;
    private final PasswordEncoder passwordEncoder;

	public User findByTypeAndTypeValue(SFunction<User,String> function, String value) {
		User dao = this.baseMapper
				.selectOne(Wrappers.<User>lambdaQuery().eq(function,value));
		return dao;
	}

	@Override
	public User findUserByUsername(String username) {
		return findByTypeAndTypeValue(User::getUsername,username);
	}

	@Override
	public User findUserByMobile(String mobile) {
		return findByTypeAndTypeValue(User::getMobile,mobile);
	}

	@Override
	public User findUserByEmail(String email) {
		return findByTypeAndTypeValue(User::getEmail,email);
	}

    @Override
    public void addUser(UserSaveDTO dto) {
        final long count = super.count(Wraps.<User>lbQ().eq(User::getUsername, dto.getUsername()));
        if (count > 0) {
            throw CheckedException.badRequest("账号已存在");
        }
        final User user = BeanUtil.toBean(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        super.save(user);
    }

    @Override
    public List<User> list(DataScope scope) {
        return baseMapper.list(scope);
    }

    @Override
    public IPage<UserResp> findPage(IPage<User> page, LbqWrapper<User> wrapper) {
        return baseMapper.findPage(page, wrapper);
    }

    @Override
    public void changePassword(Long userId, String orgPassword, String newPassword) {
        final User user = Optional.ofNullable(this.baseMapper.selectById(userId))
                .orElseThrow(() -> CheckedException.notFound("用户不存在"));
        if (!passwordEncoder.matches(orgPassword, user.getPassword())) {
            throw CheckedException.badRequest("原始密码错误");
        }
        User record = new User();
        record.setId(userId);
        record.setPassword(passwordEncoder.encode(newPassword));
        this.userMapper.updateById(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        final User user = Optional.ofNullable(getById(id)).orElseThrow(() -> CheckedException.notFound("用户不存在"));
        if (user.getReadonly()) {
            throw CheckedException.badRequest("内置用户不允许删除");
        }
        baseMapper.deleteById(id);
        userRoleMapper.delete(Wraps.<UserRole>lbQ().eq(UserRole::getUserId, id));
    }

	@Override
	public List<User> findUserBySocialUserUuidAndSource(String uuid, String source) {
		return baseMapper.findUserBySocialUserUuidAndSource(uuid,source);
	}

	@Override
	public void bindSocialUser(AuthUser authUser, Long userId) {
		String uuid = authUser.getUuid();
		String source = authUser.getSource();
		String username = authUser.getUsername();
		String nickname = authUser.getNickname();
		String avatar = authUser.getAvatar();
		String email = authUser.getEmail();
		AuthToken token = authUser.getToken();
		String accessToken = token.getAccessToken();
		String refreshToken = token.getRefreshToken();
		int expireIn = token.getExpireIn();
		int refreshTokenExpireIn = token.getRefreshTokenExpireIn();
		String scope = token.getScope();
		String tokenType = token.getTokenType();
		SocialUser socialUser = SocialUser.builder()
				.uuid(uuid)
				.source(source)
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.expireIn(expireIn)
				.refreshTokenExpireIn(refreshTokenExpireIn)
				.scope(scope)
				.tokenType(tokenType)
				.username(username)
				.nickname(nickname)
				.email(email)
				.avatar(avatar)
				.build();
		sysSocialUserMapper.insert(socialUser);
		SocialUserAuth socialUserAuth = SocialUserAuth.builder()
				.socialUserId(socialUser.getId())
				.userId(userId)
				.build();
		sysSocialUserAuthMapper.insert(socialUserAuth);
	}
}
