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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gitee.common.core.constant.CommonConstants;
import com.gitee.common.security.exception.PlatformAuth2Exception;

import lombok.SneakyThrows;

/**
 * @author lihaifeng
 * @date 2018/11/16
 * <p>
 * OAuth2 异常格式化
 */
public class PlatformAuth2ExceptionSerializer extends StdSerializer<PlatformAuth2Exception> {
	public PlatformAuth2ExceptionSerializer() {
		super(PlatformAuth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(PlatformAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		gen.writeStartObject();
		gen.writeObjectField("code", CommonConstants.FAIL);
		gen.writeStringField("msg", value.getMessage());
		gen.writeStringField("data", value.getErrorCode());
		gen.writeEndObject();
	}
}
