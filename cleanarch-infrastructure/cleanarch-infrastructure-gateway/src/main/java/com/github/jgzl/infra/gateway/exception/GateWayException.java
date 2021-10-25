package com.github.jgzl.infra.gateway.exception;
import lombok.Data;

/**
 * 网关异常
 * Created by smlz on 2019/12/26.
 */
@Data
public class GateWayException extends RuntimeException {

	private String code;

	private String msg;

}
