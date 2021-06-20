package com.github.jgzl.common.core.util;

import com.github.jgzl.common.core.constant.CommonConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author lihaifeng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "响应信息主体")
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 返回标记：成功标记=0，失败标记=1
	 */
	@ApiModelProperty(value = "返回标记")
	private String code;

	/**
	 * 返回信息
	 */
	@ApiModelProperty(value = "返回信息")
	private String msg;

	/**
	 * 返回信息
	 */
	@ApiModelProperty(value = "返回信息")
	private T data;

	public static <T> Result<T> ok() {
		return restResult(null, CommonConstants.SUCCESS, null);
	}

	public static <T> Result<T> ok(T data) {
		return restResult(data, CommonConstants.SUCCESS, null);
	}

	public static <T> Result<T> ok(T data, String msg) {
		return restResult(data, CommonConstants.SUCCESS, msg);
	}

	public static <T> Result<T> failed() {
		return restResult(null, CommonConstants.FAIL, null);
	}

	public static <T> Result<T> failed(String msg) {
		return restResult(null, CommonConstants.FAIL, msg);
	}

	public static <T> Result<T> failed(T data) {
		return restResult(data, CommonConstants.FAIL, null);
	}

	public static <T> Result<T> failed(T data, String msg) {
		return restResult(data, CommonConstants.FAIL, msg);
	}

	private static <T> Result<T> restResult(T data, String code, String msg) {
		Result<T> apiResult = new Result<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		return apiResult;
	}
}
