package com.gitee.common.core.base;

import lombok.Data;

/**
 * @author: lihaifeng
 * @version: 2019-08-02 11:18
 * 模拟out和ref语法
 **/
@Data
public class Ref<T> {
    private volatile T data;

    public Ref(T data) {
        this.data = data;
    }

    public boolean isNull() {
        return data == null;
    }
}
