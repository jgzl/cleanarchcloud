package com.github.jgzl.infra.upms.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.jgzl.common.api.dataobject.BaseDo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Data
@ApiModel(value = "文件")
@EqualsAndHashCode(callSuper = true)
public class SysFile extends BaseDo<SysFile> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "文件编号")
	private Long id;

	/**
	 * 文件名
	 */
	@ApiModelProperty(value = "文件名")
	private String fileName;

	/**
	 * 原文件名
	 */
	@ApiModelProperty(value = "原始文件名")
	private String original;

	/**
	 * 容器名称
	 */
	@ApiModelProperty(value = "存储桶名称")
	private String bucketName;

	/**
	 * 文件类型
	 */
	@ApiModelProperty(value = "文件类型")
	private String type;

	/**
	 * 文件大小
	 */
	@ApiModelProperty(value = "文件大小")
	private Long fileSize;

}
