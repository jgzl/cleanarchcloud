package com.github.jgzl.common.security.component;
import cn.hutool.core.util.StrUtil;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.security.exception.BusinessException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.Set;

/**
 * @author lihaifeng
 * @date 2020/8/30
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 *
	 * 业务异常统一处理
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler( {BusinessException.class})
	public Result handleBusinessException(BusinessException e) {
		return Result.failed(e.getMessage());
	}

	@ExceptionHandler( {FeignException.class})
	public Result handleFeignException(FeignException e) {
		Result<String> result = new Result<>();
		result.setMsg(e.getLocalizedMessage());
		result.setCode(e.status()+"");
		return result;
	}

	/**
	 * 参数验证 异常处理
	 *
	 * @param ex ConstraintViolationException
	 * @return
	 */
	@ExceptionHandler( {javax.validation.ConstraintViolationException.class})
	public Result handleCiException(ConstraintViolationException ex) {
		final Result result = Result.failed();
		final Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		for (ConstraintViolation<?> violation : violations) {
			if (!StrUtil.isBlank(violation.getMessage())) {
				result.setMsg(violation.getMessage());
				break;
			}
		}
		return result;
	}


	/**
	 *
	 * 兜底异常处理
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler( {Exception.class})
	public Result handle(Exception ex) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		final Result result = Result.failed("系统错误");
		if (ex instanceof HttpMessageNotReadableException
				|| ex instanceof MethodArgumentTypeMismatchException) {
			result.setMsg("参数解析失败");
			status = HttpStatus.BAD_REQUEST;
		} else if (ex instanceof HttpRequestMethodNotSupportedException) {
			result.setMsg("不支持当前请求方法");
			status = HttpStatus.METHOD_NOT_ALLOWED;
		} else if (ex instanceof HttpMediaTypeNotSupportedException) {
			result.setMsg("不支持当前媒体类型");
			status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
		} else if (ex instanceof SQLException) {
			result.setMsg("服务运行SQL异常");
		} else if (ex instanceof MissingServletRequestParameterException) {
			status = HttpStatus.BAD_REQUEST;
			result.setMsg("请求参数不全");
		} else if (ex instanceof MaxUploadSizeExceededException) {
			status = HttpStatus.PAYLOAD_TOO_LARGE;
			result.setMsg("上传文件总大小不允许超过" + 100);
		} else if (ex instanceof MethodArgumentNotValidException) {
			status = HttpStatus.BAD_REQUEST;
			BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
			if (bindingResult.hasErrors()) {
				final FieldError fieldError = bindingResult.getFieldError();
				result.setMsg(fieldError.getDefaultMessage());
			}
		} else if (ex instanceof BindException) {
			BindingResult bindingResult = ((BindException) ex).getBindingResult();
			if (bindingResult.hasErrors()) {
				final FieldError fieldError = bindingResult.getFieldError();
				result.setMsg(fieldError.getDefaultMessage());
			}
		}
		log.error("兜底异常", ex);
		result.setCode(status.value()+"");
		return result;
	}
}
