package com.github.jgzl.common.core.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.jgzl.common.core.model.enums.IntEnum;
import com.github.jgzl.common.core.model.enums.CommonError;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author lihaifeng
 */
@Setter
@Getter
@Accessors(chain = true)
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String OPERATION_SUCCESS = "操作成功!";
	public static final String DEF_ERROR_MESSAGE = "系统繁忙，请稍候再试";
	public static final String HYSTRIX_ERROR_MESSAGE = "请求超时，请稍候再试";
	public static final int SUCCESS_CODE = 200;
	public static final int FAIL_CODE = -1;
	public static final int TIMEOUT_CODE = -2;
	/**
	 * 统一参数验证异常
	 */
	public static final int VALID_EX_CODE = -9;
	public static final int OPERATION_EX_CODE = -400;

	/**
	 * 是否执行默认操作
	 */
	@JsonIgnore
	private Boolean defExec = false;

	/**
	 * 是否成功
	 */
	private boolean successful = true;

	/**
	 * 消息id
	 */
	private int code;

	/**
	 * 消息内容
	 */
	private String message;
	/**
	 * 时间戳：Date 类型
	 */
	private long timestamp;
	/**
	 * 返回数据
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private JSONObject ext;


	private Result(Result.Builder<T> builder) {
		this.code = builder.code;
		this.message = builder.message;
		this.timestamp = builder.timestamp;
		this.successful = builder.successful;
		this.data = builder.data;
		this.ext = builder.ext;
	}

	private Result() {
		super();
	}

	public Result(int code, T data, String message) {
		this.code = code;
		this.data = data;
		this.message = message;
		this.successful = code == SUCCESS_CODE;
		this.defExec = false;
	}

	public Result(int code, T data, String message, boolean successful, boolean defExec) {
		this.code = code;
		this.data = data;
		this.message = message;
		this.successful = successful;
		this.defExec = defExec;
	}

	/**
	 * 请求成功消息
	 *
	 * @return RPC调用结果
	 */
	public static <E> Result<E> success() {
		return new Result<>(SUCCESS_CODE, null, OPERATION_SUCCESS);
	}


	public static <E> Result<E> result(int code, E data, String msg) {
		return new Result<>(code, data, msg);
	}

	public static <E> Result<E> successDef(E data) {
		return new Result<>(SUCCESS_CODE, data, OPERATION_SUCCESS, true, true);
	}

	public static <E> Result<E> successDef() {
		return new Result<>(SUCCESS_CODE, null, OPERATION_SUCCESS, true, true);
	}

	public static <E> Result<E> successDef(E data, String msg) {
		return new Result<>(SUCCESS_CODE, data, msg, true, true);
	}


	public static Result<String> SUCCESS = getResponse(SUCCESS_CODE, OPERATION_SUCCESS, true, null);


	public static <T> Result<T> success(T data) {
		return getResponse(SUCCESS_CODE, OPERATION_SUCCESS, true, data);
	}

	public static <T> Result<T> success(String message, T data) {
		return getResponse(SUCCESS_CODE, message, true, data);
	}

	public static <T> Result<T> success(String message) {
		return success(message, null);
	}

	public static Result<String> success(int code) {
		return success(code, OPERATION_SUCCESS);
	}

	public static Result<String> success(int code, String message) {
		return success(code, message, null);
	}

	public static <T> Result<T> ok(T data) {
		return success(SUCCESS_CODE, OPERATION_SUCCESS, data);
	}

	public static <T> Result<T> success(int code, T data) {
		return getResponse(code, OPERATION_SUCCESS, true, data);
	}

	public static <T> Result<T> exists(String label, JSONObject ext) {
		final int code = CommonError.DATA_EXISTS.type();
		final String message = String.format(CommonError.DATA_EXISTS.desc(), label);
		return new Builder<T>(code, System.currentTimeMillis(), false)
				.message(message).ext(ext).data(null).build();
	}

	public static <E> Result<E> fail(String msg, Object... args) {
		String message = (msg == null || msg.isEmpty()) ? DEF_ERROR_MESSAGE : msg;
		return new Result<>(OPERATION_EX_CODE, null, String.format(message, args));
	}

	/**
	 * 请求失败消息，根据异常类型，获取不同的提供消息
	 *
	 * @param throwable 异常
	 * @return RPC调用结果
	 */
	public static <E> Result<E> fail(Throwable throwable) {
		return fail(FAIL_CODE, throwable != null ? throwable.getMessage() : DEF_ERROR_MESSAGE);
	}

	public static <E> Result<E> validFail(String msg) {
		return new Result<>(VALID_EX_CODE, null, (msg == null || msg.isEmpty()) ? DEF_ERROR_MESSAGE : msg);
	}

	public static <E> Result<E> validFail(String msg, Object... args) {
		String message = (msg == null || msg.isEmpty()) ? DEF_ERROR_MESSAGE : msg;
		return new Result<>(VALID_EX_CODE, null, String.format(message, args));
	}

	public static <T> Result<T> success(int code, String message, T data) {
		return getResponse(code, message, true, data);
	}

	public static <T> Result<T> fail(String message, T data) {
		return getResponse(400, message, false, data);
	}

	public static <T> Result<T> fail(String message) {
		return fail(message, null);
	}

	public static <T> Result<T> fail(IntEnum intEnum) {
		return getResponse(intEnum.type(), intEnum.desc(), false, null);
	}

	public static <T> Result<T> fail(IntEnum intEnum, T data) {
		return getResponse(intEnum.type(), intEnum.desc(), false, data);
	}

	public static <T> Result<T> fail(IntEnum intEnum, String format) {
		return getResponse(intEnum.type(), String.format(intEnum.desc(), format), false, null);
	}

	public static <T> Result<T> fail(int code, String message) {
		return getResponse(code, message, false, null);
	}

	public static <T> Result<T> fail(int code, String message, T data) {
		return getResponse(code, message, false, data);
	}

	public static <T> Result<T> getResponse(Boolean flag) {
		return flag ? success(OPERATION_SUCCESS) : fail(DEF_ERROR_MESSAGE);
	}

	public static <T> Result<T> getResponse(boolean successful, String message) {
		return successful ? success(message) : fail(message);
	}

	public static <T> Result<T> getResponse(boolean successful, String message, T data) {
		return successful ? success(message, data) : fail(message);
	}

	public static <T> Result<T> getResponse(int code, String message, boolean successful, T data) {
		return new Result.Builder<T>(code, System.currentTimeMillis(), successful).message(message).data(data).build();
	}

	public static class Builder<T> {
		private final int code;
		private String message;
		private final long timestamp;
		private final boolean successful;
		private T data;
		private JSONObject ext;

		public Builder(int code, long timestamp, boolean successful) {
			this.code = code;
			this.timestamp = timestamp;
			this.successful = successful;
		}

		public Result.Builder<T> message(String message) {
			this.message = message;
			return this;
		}

		public Result.Builder<T> ext(JSONObject ext) {
			this.ext = ext;
			return this;
		}

		public Result.Builder<T> data(T data) {
			this.data = data;
			return this;
		}

		public Result<T> build() {
			return new Result<>(this);
		}
	}

	@Override
	public String toString() {
		return JSONUtil.toJsonStr(this);
	}

}