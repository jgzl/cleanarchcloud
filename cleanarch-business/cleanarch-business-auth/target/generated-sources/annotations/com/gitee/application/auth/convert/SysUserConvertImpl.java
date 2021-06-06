package com.gitee.application.auth.convert;

import com.gitee.common.security.dataobject.SysUserDO;
import com.gitee.common.security.vo.SysUserVO;
import com.gitee.common.security.vo.UserVO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-06T17:15:51+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_292 (AdoptOpenJDK)"
)
public class SysUserConvertImpl implements SysUserConvert {

    @Override
    public SysUserVO convert(SysUserDO user) {
        if ( user == null ) {
            return null;
        }

        SysUserVO sysUserVO = new SysUserVO();

        sysUserVO.setCreateUser( user.getCreateUser() );
        sysUserVO.setCreateDate( user.getCreateDate() );
        sysUserVO.setUpdateUser( user.getUpdateUser() );
        sysUserVO.setUpdateDate( user.getUpdateDate() );
        sysUserVO.setVersion( user.getVersion() );
        sysUserVO.setDeleted( user.getDeleted() );
        sysUserVO.setUsername( user.getUsername() );
        sysUserVO.setNickname( user.getNickname() );
        sysUserVO.setPassword( user.getPassword() );
        sysUserVO.setMobile( user.getMobile() );
        sysUserVO.setEmail( user.getEmail() );
        sysUserVO.setLoginCount( user.getLoginCount() );
        sysUserVO.setLoginErrorCount( user.getLoginErrorCount() );
        sysUserVO.setLoginTime( user.getLoginTime() );
        sysUserVO.setLoginStatus( user.getLoginStatus() );
        sysUserVO.setAvatar( user.getAvatar() );

        sysUserVO.setUserId( String.valueOf(user.getUserId()) );

        return sysUserVO;
    }

    @Override
    public SysUserDO convert(SysUserVO user) {
        if ( user == null ) {
            return null;
        }

        SysUserDO sysUserDO = new SysUserDO();

        sysUserDO.setCreateUser( user.getCreateUser() );
        sysUserDO.setCreateDate( user.getCreateDate() );
        sysUserDO.setUpdateUser( user.getUpdateUser() );
        sysUserDO.setUpdateDate( user.getUpdateDate() );
        sysUserDO.setVersion( user.getVersion() );
        sysUserDO.setDeleted( user.getDeleted() );
        sysUserDO.setUsername( user.getUsername() );
        sysUserDO.setNickname( user.getNickname() );
        sysUserDO.setPassword( user.getPassword() );
        sysUserDO.setMobile( user.getMobile() );
        sysUserDO.setEmail( user.getEmail() );
        sysUserDO.setLoginCount( user.getLoginCount() );
        sysUserDO.setLoginErrorCount( user.getLoginErrorCount() );
        sysUserDO.setLoginTime( user.getLoginTime() );
        sysUserDO.setLoginStatus( user.getLoginStatus() );
        sysUserDO.setAvatar( user.getAvatar() );

        sysUserDO.setUserId( user.getUserId()!=null&&!user.getUserId().equals("")?Long.valueOf(user.getUserId()):null );

        return sysUserDO;
    }

    @Override
    public UserVO convertUserDetails(SysUserDO user) {
        if ( user == null ) {
            return null;
        }

        UserVO userVO = new UserVO();

        userVO.setCreateUser( user.getCreateUser() );
        userVO.setCreateDate( user.getCreateDate() );
        userVO.setUpdateUser( user.getUpdateUser() );
        userVO.setUpdateDate( user.getUpdateDate() );
        userVO.setVersion( user.getVersion() );
        userVO.setDeleted( user.getDeleted() );
        userVO.setUserId( user.getUserId() );
        userVO.setUsername( user.getUsername() );
        userVO.setNickname( user.getNickname() );
        userVO.setPassword( user.getPassword() );
        userVO.setMobile( user.getMobile() );
        userVO.setEmail( user.getEmail() );
        userVO.setLoginCount( user.getLoginCount() );
        userVO.setLoginErrorCount( user.getLoginErrorCount() );
        userVO.setLoginTime( user.getLoginTime() );
        userVO.setLoginStatus( user.getLoginStatus() );
        userVO.setAvatar( user.getAvatar() );

        return userVO;
    }

    @Override
    public SysUserDO convertUserDetails(UserVO user) {
        if ( user == null ) {
            return null;
        }

        SysUserDO sysUserDO = new SysUserDO();

        sysUserDO.setCreateUser( user.getCreateUser() );
        sysUserDO.setCreateDate( user.getCreateDate() );
        sysUserDO.setUpdateUser( user.getUpdateUser() );
        sysUserDO.setUpdateDate( user.getUpdateDate() );
        sysUserDO.setVersion( user.getVersion() );
        sysUserDO.setDeleted( user.getDeleted() );
        sysUserDO.setUserId( user.getUserId() );
        sysUserDO.setUsername( user.getUsername() );
        sysUserDO.setNickname( user.getNickname() );
        sysUserDO.setPassword( user.getPassword() );
        sysUserDO.setMobile( user.getMobile() );
        sysUserDO.setEmail( user.getEmail() );
        sysUserDO.setLoginCount( user.getLoginCount() );
        sysUserDO.setLoginErrorCount( user.getLoginErrorCount() );
        sysUserDO.setLoginTime( user.getLoginTime() );
        sysUserDO.setLoginStatus( user.getLoginStatus() );
        sysUserDO.setAvatar( user.getAvatar() );

        return sysUserDO;
    }
}
