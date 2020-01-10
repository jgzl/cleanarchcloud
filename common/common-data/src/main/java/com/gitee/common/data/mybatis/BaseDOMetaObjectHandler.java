package com.gitee.common.data.mybatis;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

public class BaseDOMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
//        this.setFieldValByName("createUser", "admin1", metaObject);//版本号3.0.6以及之前的版本
        this.setInsertFieldValByName("createUser", 1L, metaObject);//@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
        this.setInsertFieldValByName("createDate", DateTime.now(), metaObject);
        this.setInsertFieldValByName("version", 0, metaObject);
        this.setInsertFieldValByName("deleted", 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setInsertFieldValByName("updateUser", 1L, metaObject);//@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
        this.setInsertFieldValByName("updateDate", DateTime.now(), metaObject);
    }
}