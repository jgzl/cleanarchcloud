package com.github.jgzl.common.security.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.security.exception.CustomOauth2Exception;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 * @date 2020/11/16
 * OAuth2 异常格式化
 */
@Slf4j
public class ExtendOauth2ExceptionSerializer extends StdSerializer<CustomOauth2Exception> {
	public ExtendOauth2ExceptionSerializer() {
		super(CustomOauth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(CustomOauth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		gen.writeStartObject();
		gen.writeObjectField("code", Result.FAIL_CODE);
		gen.writeStringField("message", value.getMessage());
		gen.writeStringField("data", value.getOAuth2ErrorCode());
		gen.writeEndObject();
	}
}
