/*
 *    Copyright [2020] [lihaifeng,xuhang]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gitee.common.upms.base;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
@ApiModel("基础对象")
public class BaseModel<M> extends Model<BaseModel<M>> implements Serializable,Cloneable {
  /** 乐观锁 */
  @Version
  @ApiModelProperty("乐观锁")
  private Integer revision ;
  /** 创建人 */
  @ApiModelProperty("创建人")
  @TableField(fill = FieldFill.INSERT)
  private Long createdBy ;
  /** 创建时间 */
  @ApiModelProperty("创建时间")
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createdTime ;
  /** 更新人 */
  @ApiModelProperty("更新人")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Long updatedBy ;
  /** 更新时间 */
  @ApiModelProperty("更新时间")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updatedTime ;
}