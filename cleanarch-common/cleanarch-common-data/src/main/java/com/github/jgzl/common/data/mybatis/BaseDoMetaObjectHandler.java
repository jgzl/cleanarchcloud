package com.github.jgzl.common.data.mybatis;
import java.time.LocalDateTime;
import com.github.jgzl.common.api.vo.SysUserVo;
import com.github.jgzl.common.data.external.ReceiveUserInfoService;
import org.apache.ibatis.reflection.MetaObject;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.springframework.util.ObjectUtils;

/**
 * @author lihaifeng
 */
public class BaseDoMetaObjectHandler implements MetaObjectHandler {

	private ReceiveUserInfoService receiveUserInfoService;

	BaseDoMetaObjectHandler() {}

	BaseDoMetaObjectHandler(ReceiveUserInfoService receiveUserInfoService) {
		this.receiveUserInfoService = receiveUserInfoService;
	}

	@Override
	public void insertFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, "createUser", String.class, fetchAccount());
		this.strictInsertFill(metaObject, "createDate", LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, "updateUser", String.class, fetchAccount());
		this.strictInsertFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, "version", Integer.class, 0);
		this.strictInsertFill(metaObject, "delFlag", Integer.class, 0);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictUpdateFill(metaObject, "updateUser", String.class, fetchAccount());
		this.strictUpdateFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
	}

	public String fetchAccount() {
		SysUserVo currentUserAccount = receiveUserInfoService.getCurrentUserAccount();
		if (currentUserAccount!=null) {
			if (!ObjectUtils.isEmpty(currentUserAccount.getUsername())) {
				return currentUserAccount.getUsername();
			}
		}
		return "admin";
	}
}