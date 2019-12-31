package com.gitee.common.core.base;

import com.gitee.common.core.config.CoreProperties;
import com.gitee.common.core.util.PropertyUtils;
import com.gitee.common.core.util.ReflectionUtils;

import lombok.val;

/**
 * @author: lihaifeng
 * 默认环境枚举
 * @version: 2019-05-27 13:38
 **/
public enum BsfEnvironmentEnum {
    JOB_DEV(Environment.dev,
        ReflectionUtils.tryGetStaticFieldValue("com.gitee.common.core.base.BsfBaseConfig", "job_dev", ""),
        "bsf_job_url"),
    APOLLO_DEV(Environment.dev,
        ReflectionUtils.tryGetStaticFieldValue("com.gitee.common.core.base.BsfBaseConfig", "apollo_dev", ""),
        "bsf_apollo_url"),
    APOLLO_PRD(Environment.prd,
        ReflectionUtils.tryGetStaticFieldValue("com.gitee.common.core.base.BsfBaseConfig", "apollo_prd", ""),
        "bsf_apollo_url"),
    CAT_DEV(Environment.dev,
        ReflectionUtils.tryGetStaticFieldValue("com.gitee.common.core.base.BsfBaseConfig", "cat_dev", ""),
        "bsf_cat_url"),
    ELK_DEV(Environment.dev,
        ReflectionUtils.tryGetStaticFieldValue("com.gitee.common.core.base.BsfBaseConfig", "elk_dev", ""),
        "bsf_elk_url"),
    RocketMQ_DEV(Environment.dev,
        ReflectionUtils.tryGetStaticFieldValue("com.gitee.common.core.base.BsfBaseConfig", "rocketmq_dev", ""),
        "bsf_rocketmq_url"),
    EUREKA_DEV(Environment.dev,
        ReflectionUtils.tryGetStaticFieldValue("com.gitee.common.core.base.BsfBaseConfig", "eureka_dev", ""),
        "bsf_eureka_url"),
    REDIS_DEV(Environment.dev,
        ReflectionUtils.tryGetStaticFieldValue("com.gitee.common.core.base.BsfBaseConfig", "redis_dev", ""),
        "bsf_redis_url"),
    ES_DEV(Environment.dev,
        ReflectionUtils.tryGetStaticFieldValue("com.gitee.common.core.base.BsfBaseConfig", "es_dev", ""), "bsf_es_url"),
    FILE_DEV(Environment.dev,
        ReflectionUtils.tryGetStaticFieldValue("com.gitee.common.core.base.BsfBaseConfig", "file_dev", ""),
        "bsf_file_url");

    private Environment env;

    private String url;

    private String serverkey;

    BsfEnvironmentEnum(Environment env, String url, String serverkey) {
        this.env = env;
        this.url = url;
        this.serverkey = serverkey;
    }

    public static BsfEnvironmentEnum get(String serverKey, BsfEnvironmentEnum defaultValue) {
        for (val e : BsfEnvironmentEnum.values()) {
            if (e.getServerkey().equalsIgnoreCase(serverKey) && e.getEnv().toString()
                .equalsIgnoreCase(PropertyUtils.getPropertyCache(CoreProperties.BsfEnv, ""))) {
                return e;
            }
        }
        return defaultValue;
    }

    public Environment getEnv() {
        return env;
    }

    public String getUrl() {
        return url;
    }

    public String getServerkey() {
        return serverkey;
    }
}
