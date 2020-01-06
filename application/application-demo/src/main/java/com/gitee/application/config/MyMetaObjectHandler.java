package com.gitee.application.config;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
//        this.setFieldValByName("operator", "Jerry", metaObject);//版本号3.0.6以及之前的版本
        this.setInsertFieldValByName("createUser", "admin", metaObject);//@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
        this.setInsertFieldValByName("createDate", DateTime.now(), metaObject);
        this.setInsertFieldValByName("updateUser", "admin", metaObject);//@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
        this.setInsertFieldValByName("updateDate", DateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setInsertFieldValByName("updateUser", "admin", metaObject);//@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
        this.setInsertFieldValByName("updateDate", DateTime.now(), metaObject);
    }
}