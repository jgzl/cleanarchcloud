package com.github.jgzl.common.security.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.jgzl.common.core.constant.CommonConstants;
import com.github.jgzl.common.security.exception.ExtendOauth2Exception;
import lombok.SneakyThrows;

/**
 * @author lihaifeng
 * @date 2018/11/16
 * <p>
 * OAuth2 异常格式化
 */
public class ExtendAuth2ExceptionSerializer extends StdSerializer<ExtendOauth2Exception> {

	public ExtendAuth2ExceptionSerializer() {
		super(ExtendOauth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(ExtendOauth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		gen.writeStartObject();
		gen.writeObjectField("code", CommonConstants.FAIL);
		gen.writeStringField("msg", value.getMessage());
		gen.writeStringField("data", value.getErrorCode());
		gen.writeEndObject();
	}

}
