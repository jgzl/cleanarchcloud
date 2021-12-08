package com.github.jgzl.common.core.constant;

/**
 * @author lihaifeng
 * @date 2017/10/29
 */
public interface CommonConstants {

	/**
	 * 系统名称
	 */
	String SYS_NAME = "cleanarch";

	/**
	 * header 中租户ID
	 */
	String TENANT_ID = "TENANT-ID";

	/**
	 * header 中版本信息
	 */
	String VERSION = "VERSION";

	/**
	 * 租户ID
	 */
	Integer TENANT_ID_1 = 1;

	/**
	 * 删除
	 */
	String STATUS_DEL = "1";

	/**
	 * 正常
	 */
	String STATUS_NORMAL = "0";

	/**
	 * 锁定
	 */
	String STATUS_LOCK = "9";

	/**
	 * 菜单树根节点
	 */
	Integer MENU_TREE_ROOT_ID = -1;

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * 前端工程名
	 */
	String FRONT_END_PROJECT = "cleanarchcloud-ui";

	/**
	 * 后端工程名
	 */
	String BACK_END_PROJECT = "cleanarchcloud";

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 0;

	/**
	 * 失败标记
	 */
	Integer FAIL = 1;

	/**
	 * 默认存储bucket
	 */
	String BUCKET_NAME = "cleanarch";

	/**
	 * 滑块验证码
	 */
	String IMAGE_CODE_TYPE = "blockPuzzle";

	/**
	 * 验证码开关
	 */
	String CAPTCHA_FLAG = "captcha_flag";

	/**
	 * 密码传输是否加密
	 */
	String ENC_FLAG = "enc_flag";

}
