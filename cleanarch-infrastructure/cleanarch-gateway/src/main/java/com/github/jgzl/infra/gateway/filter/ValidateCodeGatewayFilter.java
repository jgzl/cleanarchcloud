package com.github.jgzl.infra.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.constant.CommonConstants;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.core.constant.enums.CaptchaFlagTypeEnum;
import com.github.jgzl.common.core.exception.ValidateCodeException;
import com.github.jgzl.common.core.util.R;
import com.github.jgzl.common.core.util.SpringContextHolder;
import com.github.jgzl.common.core.util.WebUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author lihaifeng
 * @date 2020/5/19 登录逻辑验证码处理
 */
@Slf4j
@Component
@AllArgsConstructor
@SuppressWarnings("all")
public class ValidateCodeGatewayFilter extends AbstractGatewayFilterFactory {

	private final ObjectMapper objectMapper;

	private final RedissonClient redisson;

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			// 不是账号密码登录、短信登录，直接向下执行
			if (!StrUtil.containsAnyIgnoreCase(request.getURI().getPath(), SecurityConstants.OAUTH_TOKEN_URL,
					SecurityConstants.SMS_TOKEN_URL)) {
				return chain.filter(exchange);
			}

			// 刷新token，直接向下执行
			String grantType = request.getQueryParams().getFirst("grant_type");
			if (StrUtil.equals(SecurityConstants.REFRESH_TOKEN, grantType)) {
				return chain.filter(exchange);
			}

			// 判断客户端是否跳过检验
			if (!isCheckCaptchaClient(request)) {
				return chain.filter(exchange);
			}

			try {
				// 校验验证码
				checkCode(request);
			}
			catch (Exception e) {
				ServerHttpResponse response = exchange.getResponse();
				response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
				response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
				try {
					return response.writeWith(Mono.just(
							response.bufferFactory().wrap(objectMapper.writeValueAsBytes(R.failed(e.getMessage())))));
				}
				catch (JsonProcessingException e1) {
					log.error("对象输出异常", e1);
				}
			}

			return chain.filter(exchange);
		};
	}

	/**
	 * 是否需要校验客户端，根据client 查询客户端配置
	 * @param request 请求
	 * @return true 需要校验， false 不需要校验
	 */
	private boolean isCheckCaptchaClient(ServerHttpRequest request) {
		String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		String clientId = WebUtils.extractClientId(header).orElse(null);
		// 获取租户拼接区分租户的key
		String tenantId = request.getHeaders().getFirst(CommonConstants.TENANT_ID);
		String key = String.format("%s:%s:%s", StrUtil.isBlank(tenantId) ? CommonConstants.TENANT_ID_1 : tenantId,
				CacheConstants.CLIENT_FLAG, clientId);

		Object val = redisson.getBucket(key).get();

		// 当配置不存在时，默认需要校验
		if (val == null) {
			return true;
		}

		JSONObject information = JSONUtil.parseObj(val.toString());
		if (StrUtil.equals(CaptchaFlagTypeEnum.OFF.getType(), information.getStr(CommonConstants.CAPTCHA_FLAG))) {
			return false;
		}
		return true;
	}

	/**
	 * 检查code
	 * @param request
	 */
	@SneakyThrows
	private void checkCode(ServerHttpRequest request) {
		String code = request.getQueryParams().getFirst("code");

		if (StrUtil.isBlank(code)) {
			throw new ValidateCodeException("验证码不能为空");
		}

		String randomStr = request.getQueryParams().getFirst("randomStr");

		// 若是滑块登录
		if (CommonConstants.IMAGE_CODE_TYPE.equalsIgnoreCase(randomStr)) {
			CaptchaService captchaService = SpringContextHolder.getBean(CaptchaService.class);
			CaptchaVO vo = new CaptchaVO();
			vo.setCaptchaVerification(code);
			vo.setCaptchaType(CommonConstants.IMAGE_CODE_TYPE);
			if (!captchaService.verification(vo).isSuccess()) {
				throw new ValidateCodeException("验证码不能为空");
			}
			return;
		}

		String mobile = request.getQueryParams().getFirst("mobile");
		if (StrUtil.isNotBlank(mobile)) {
			randomStr = mobile;
		}

		String key = CacheConstants.DEFAULT_CODE_KEY + randomStr;

		RKeys keys = redisson.getKeys();
		if (keys.countExists(key)==0) {
			throw new ValidateCodeException("验证码不合法");
		}

		Object codeObj = redisson.getBucket(key).get();

		if (codeObj == null) {
			throw new ValidateCodeException("验证码不合法");
		}

		String saveCode = codeObj.toString();
		if (StrUtil.isBlank(saveCode)) {
			keys.delete(key);
			throw new ValidateCodeException("验证码不合法");
		}

		if (!StrUtil.equals(saveCode, code)) {
			keys.delete(key);
			throw new ValidateCodeException("验证码不合法");
		}

		keys.delete(key);
	}

}
