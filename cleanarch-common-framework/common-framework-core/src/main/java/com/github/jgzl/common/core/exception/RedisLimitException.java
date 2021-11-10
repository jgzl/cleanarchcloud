package com.github.jgzl.common.core.exception;

import com.github.jgzl.common.core.model.enums.CommonError;
import com.github.jgzl.common.core.model.enums.IntEnum;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * 检查型异常
 *
 * @author Levin
 * @since 2019-03-13
 */
@Setter
@EqualsAndHashCode(callSuper = true)
public class RedisLimitException extends RuntimeException {

    private int code;

    public RedisLimitException(IntEnum exception) {
        super(exception.desc());
        this.setCode(exception.type());
    }

    public RedisLimitException(IntEnum exception, String message) {
        super(message);
        this.setCode(exception.type());
    }

    public RedisLimitException(String message) {
        super(message);
        this.setCode(CommonError.TOO_MANY_REQUESTS.type());
    }

    public RedisLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisLimitException(Throwable cause) {
        super(cause);
    }

    public RedisLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}