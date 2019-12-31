package com.gitee.common.core.base;

import java.util.HashMap;

/**
 * @author: lihaifeng
 * @version: 2019-08-10 16:00
 **/
public enum BsfEventEnum {
    PropertyCacheUpdateEvent(new HashMap<String, Object>().getClass(), "属性缓存更新事件");

    String desc;

    Class dataClass;

    BsfEventEnum(Class dataClass, String desc) {
        this.desc = desc;
        this.dataClass = dataClass;
    }

    public Class getDataClass() {
        return dataClass;
    }
}
