package com.gitee.common.core.base;

/**
 * @author: lihaifeng
 * @version: 2019-05-27 13:44
 **/
public enum Environment {
    dev("开发"),
    //test("测试"),
    prd("生产");

    private String name;

    Environment(String name) {
        this.name = name;
    }
}
