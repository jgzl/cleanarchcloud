package com.github.jgzl.infra.upms.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.jgzl.common.data.mybatis.SuperService;
import com.github.jgzl.common.data.mybatis.auth.DataScope;
import com.github.jgzl.common.data.mybatis.conditions.query.LbqWrapper;
import com.github.jgzl.infra.upms.domain.dto.UserSaveDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.User;
import com.github.jgzl.infra.upms.domain.vo.UserResp;
import me.zhyd.oauth.model.AuthUser;
import java.util.List;
/**
 * <p>
 * 业务接口
 * 账号
 * </p>
 *
 * @author lihaifeng
 * @since 2019-07-03
 */
public interface UserService extends SuperService<User> {

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	User findUserByUsername(String username);
	/**
	 * 根据用户名查找用户
	 * @param mobile
	 * @return
	 */
	User findUserByMobile(String mobile);
	/**
	 * 根据用户名查找用户
	 * @param email
	 * @return
	 */
	User findUserByEmail(String email);
    /**
     * 添加用户
     *
     * @param dto 用户信息
     */
    void addUser(UserSaveDTO dto);
    /**
     * 根据条件查询
     *
     * @param scope scope
     * @return 查询结果
     */
    List<User> list(DataScope scope);
    /**
     * 数据权限 分页
     *
     * @param page    page
     * @param wrapper wrapper
     * @return 查询结果
     */
    IPage<UserResp> findPage(IPage<User> page, LbqWrapper<User> wrapper);
    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param orgPassword 原始密码
     * @param newPassword 新密码
     */
    void changePassword(Long userId, String orgPassword, String newPassword);
    /**
     * 根据ID删除用户
     *
     * @param id id
     */
    void deleteById(Long id);
	List<User> findUserBySocialUserUuidAndSource(String uuid, String source);
	void bindSocialUser(AuthUser authUser, Long userId);
}
