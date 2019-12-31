package com.gitee.common.core.util;

import java.lang.reflect.Type;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gitee.common.core.serialize.JsonSerializer;

/**
 * @author: lihaifeng
 * @version: 2019-07-28 14:59
 **/
public class JsonUtils {
    public static JsonSerializer Default = new JsonSerializer();

    public static String serialize(Object object) {
        return Default.serialize(object);
    }

    public static <T> T deserialize(String str, Type type) {
        return Default.deserialize(str, type);
    }

    public static <T> T deserialize(String str, TypeReference<T> type) {
        return Default.deserialize(str, type.getType());
    }
}
