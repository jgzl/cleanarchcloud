package com.github.jgzl.infra.upms.domain.entity.message;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jgzl.common.core.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Levin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_station_message")
public class StationMessage extends SuperEntity<Long> {

    @TableField("`title`")
    private String title;

    @TableField("`level`")
    private String level;

    @TableField("`description`")
    private String description;

    @TableField("`content`")
    private String content;

    @TableField("`mark`")
    private Boolean mark;
    /**
     * 接收用户的Id
     */
    @TableField("`receive_id`")
    private Long receiveId;


}
