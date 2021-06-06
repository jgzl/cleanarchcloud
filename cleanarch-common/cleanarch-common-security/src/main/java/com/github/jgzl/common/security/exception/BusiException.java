package com.github.jgzl.common.security.exception;

/**
 * 自定义业务异常
 *
 * @author lihaifeng
 * 2019/7/5 16:01
 */
public class BusiException extends RuntimeException{

    private static final long serialVersionUID = 5064805178344553207L;

    public BusiException(String message) {
        super(message);
    }
}
