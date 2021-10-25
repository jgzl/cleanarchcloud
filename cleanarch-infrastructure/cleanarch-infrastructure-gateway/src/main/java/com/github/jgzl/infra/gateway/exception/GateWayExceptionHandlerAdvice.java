package com.github.jgzl.infra.gateway.exception;
import com.github.jgzl.common.core.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class GateWayExceptionHandlerAdvice {

	@ExceptionHandler(value = {Throwable.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result handle(Throwable throwable) {
		Result result = Result.failed("网关服务异常");
		if (throwable instanceof ResponseStatusException) {
			ResponseStatusException ex = (ResponseStatusException) throwable;
			if (HttpStatus.NOT_FOUND.value() == ex.getRawStatusCode()) {
				log.error("not found exception:{}", ex.getMessage());
				return Result.failed("服务未找到");
			}else if(HttpStatus.METHOD_NOT_ALLOWED.value() == ex.getRawStatusCode()) {
				log.error("error request method exception:{}", ex.getMessage());
				return Result.failed("请求方法不正确");
			}else if(HttpStatus.BAD_REQUEST.value() == ex.getRawStatusCode()) {
				log.error("error request exception:{}", ex.getMessage());
				return Result.failed("请求错误");
			}else if(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value() == ex.getRawStatusCode()) {
				log.error("unsupported media type exception:{}", ex.getMessage());
				return Result.failed("媒体类型不支持");
			}
			log.error("response status exception:{}", ex.getMessage());
			return Result.failed("网关服务异常");
		} else if (throwable instanceof GateWayException) {
			GateWayException ex = (GateWayException) throwable;
			log.error("GateWayException:{}", ex.getMessage());
			return Result.failed(ex.getCode(), ex.getMsg());
		}else {
			return result;
		}
	}
}
