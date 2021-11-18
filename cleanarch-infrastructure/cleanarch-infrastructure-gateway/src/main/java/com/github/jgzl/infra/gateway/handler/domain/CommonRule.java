package com.github.jgzl.infra.gateway.handler.domain;
import lombok.Data;
import java.time.LocalDateTime;
/**
 * @author lihaifeng
 */
@Data
public class CommonRule {
    private String id;
    private String method;
    private String path;
    private Boolean status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
