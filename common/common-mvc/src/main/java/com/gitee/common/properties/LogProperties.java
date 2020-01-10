package com.gitee.common.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.Aspects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
public class LogProperties {

    private static String serviceName;

    public static String getServiceName() {
        return serviceName;
    }

    @Value("${spring.application.name}")
    public void setServiceName(String serviceName) {
        LogProperties.serviceName = serviceName;
    }
}
