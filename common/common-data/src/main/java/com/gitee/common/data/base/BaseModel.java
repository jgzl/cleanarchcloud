package com.gitee.common.data.base;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
public class BaseModel<M> extends Model<BaseModel<M>> implements Serializable,Cloneable {
  /** 乐观锁 */
  @Version
  private Integer revision ;
  /** 创建人 */
  @TableField(fill = FieldFill.INSERT)
  private Long createdBy ;
  /** 创建时间 */
//  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createdTime ;
  /** 更新人 */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Long updatedBy ;
  /** 更新时间 */
//  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updatedTime ;
}
