/*
 *    Copyright [2020] [lihaifeng,xuhang]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gitee.common.security.component;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 * @date 2018/8/30
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
/*	*//**
	 * 业务异常统一处理
	 *
	 * @param e
	 * @return
	 *//*
	@ExceptionHandler( {BusiException.class})
	public ResponseEntity<Result> handleBusiException(BusiException e) {
		return ResponseEntity.ok(Result.failed(e.getMessage()));
	}

	*//**
	 * 参数验证 异常处理
	 *
	 * @param ex ConstraintViolationException
	 * @return
	 *//*
	@ExceptionHandler( {ConstraintViolationException.class})
	public ResponseEntity<Result> handleCiException(ConstraintViolationException ex) {
		final ResponseEntity<Result> result = ResponseEntity.ok(Result.failed(null));
		final Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		for (ConstraintViolation<?> violation : violations) {
			if (!StringUtils.isEmpty(violation.getMessage())) {
				final Response resultBody = result.getBody();
				assert resultBody != null;
				resultBody.setErrorMessage(violation.getMessage());
				break;
			}
		}
		return result;
	}

	*//**
	 * 兜底异常处理
	 *
	 * @param ex
	 * @return
	 *//*
	@ExceptionHandler( {Exception.class})
	public ResponseEntity<Result> handle(Exception ex) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		final Response response = Result.failed("系统错误");
		if (ex instanceof HttpMessageNotReadableException
				|| ex instanceof MethodArgumentTypeMismatchException) {
			response.setErrorMessage("参数解析失败");
			status = HttpStatus.BAD_REQUEST;
		} else if (ex instanceof HttpRequestMethodNotSupportedException) {
			response.setErrorMessage("不支持当前请求方法");
			status = HttpStatus.METHOD_NOT_ALLOWED;
		} else if (ex instanceof HttpMediaTypeNotSupportedException) {
			response.setErrorMessage("不支持当前媒体类型");
			status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
		} else if (ex instanceof SQLException) {
			response.setErrorMessage("服务运行SQL异常");
		} else if (ex instanceof MissingServletRequestParameterException) {
			status = HttpStatus.BAD_REQUEST;
			response.setErrorMessage("请求参数不全");
		} else if (ex instanceof MaxUploadSizeExceededException) {
			status = HttpStatus.PAYLOAD_TOO_LARGE;
			response.setErrorMessage("上传文件总大小不允许超过" + 100);
		} else if (ex instanceof MethodArgumentNotValidException) {
			status = HttpStatus.BAD_REQUEST;
			BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
			if (bindingResult.hasErrors()) {
				final FieldError fieldError = bindingResult.getFieldError();
				response.setErrorMessage(fieldError.getDefaultMessage());
			}
		} else if (ex instanceof BindException) {
			BindingResult bindingResult = ((BindException) ex).getBindingResult();
			if (bindingResult.hasErrors()) {
				final FieldError fieldError = bindingResult.getFieldError();
				response.setErrorMessage(fieldError.getDefaultMessage());
			}
		}
		log.error("兜底异常", ex);
		return ResponseEntity.status(status).body(response);
	}*/
}
