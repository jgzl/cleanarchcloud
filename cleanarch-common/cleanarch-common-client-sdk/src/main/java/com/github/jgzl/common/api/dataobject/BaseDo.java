package com.github.jgzl.common.api.dataobject;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;

/**
 * 基础对象
 * @author lihaifeng
 */
@Data
public class BaseDo<M> extends Model<BaseDo<M>> implements Serializable {

	/**
	 * 创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createUser;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createDate;

	/**
	 * 更新人
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateUser;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateDate;

	/**
	 * 乐观锁
	 */
	@Version
	@TableField(fill = FieldFill.INSERT)
	private Integer version;

	/**
	 * 逻辑删除
	 */
	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	private Integer delFlag;
}
