package com.gitee.common.core.http;

import com.gitee.common.core.base.BsfException;

/**
 * @author: lihaifeng
 * @version: 2019-07-22 20:34
 **/
public class HttpException extends BsfException {
    public HttpException(Throwable exp) {
        super(exp);
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }
}
