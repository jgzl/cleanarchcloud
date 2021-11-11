package com.github.jgzl.infra.upms.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.jgzl.common.data.mybatis.DictionaryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * <p>
 * ReceiverType
 * </p>
 *
 * @author Levin
 * @since 2020-02-14
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat
public enum ReceiverType implements DictionaryEnum<Integer> {

    /**
     * 1
     */
    USER(1, "用户"),
    /**
     * 角色
     */
    ROLE(2, "角色"),


    ;
    @EnumValue
    @JsonValue
    private Integer type;

	/**
	 * 描述
	 */
    private String desc;

    @JsonCreator
    public static ReceiverType of(Integer type) {
        if (type == null) {
            return null;
        }
        for (ReceiverType info : values()) {
            if (info.type.equals(type)) {
                return info;
            }
        }
        return null;
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ReceiverType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    public Integer getValue() {
        return this.type;
    }

    @Override
    public String toString() {
        return String.valueOf(type);
    }


}
