package com.gitee.application.auth.oauth2.exception;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gitee.common.core.util.Response;

/**
 * CustomOauth2Exception 序列化实现
 *
 * @author lihaifeng
 * 2019/5/6 10:29
 */
public class CustomOauth2ExceptionSerializer extends StdSerializer<CustomOauth2Exception> {

    private static final long serialVersionUID = -782578501088784448L;

    public CustomOauth2ExceptionSerializer() {
        super(CustomOauth2Exception.class);
    }

    @Override
    public void serialize(final CustomOauth2Exception e, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("errorCode", e.getOAuth2ErrorCode());
        jsonGenerator.writeStringField("errorMessage", e.getMessage());
        jsonGenerator.writeStringField("status", Response.Status.FAILED.name());
        jsonGenerator.writeEndObject();
    }
}
