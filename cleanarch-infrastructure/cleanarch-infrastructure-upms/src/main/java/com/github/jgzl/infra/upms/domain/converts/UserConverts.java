package com.github.jgzl.infra.upms.domain.converts;

import com.github.jgzl.common.data.page.BasePageConverts;
import com.github.jgzl.infra.upms.domain.dto.UserUpdateDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.User;
import com.github.jgzl.infra.upms.domain.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * @author lihaifeng
 */
@Slf4j
public class UserConverts {


    public static final User2VoConverts USER_2_VO_CONVERTS = new User2VoConverts();
    public static final UserDto2PoConverts USER_DTO_2_PO_CONVERTS = new UserDto2PoConverts();

    public static class User2VoConverts implements BasePageConverts<User, UserVO> {

        @Override
        public UserVO convert(User source) {
            if (source == null) {
                return null;
            }
            UserVO target = new UserVO();
            BeanUtils.copyProperties(source, target);
            return target;
        }
    }

    public static class UserDto2PoConverts implements BasePageConverts<UserUpdateDTO, User> {

        @Override
        public User convert(UserUpdateDTO source, Long id) {
            if (source == null) {
                return null;
            }
            User target = new User();
            BeanUtils.copyProperties(source, target);
            target.setId(id);
            return target;
        }
    }
}
