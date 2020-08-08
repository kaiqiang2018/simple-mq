package com.kaiqiang.simple.mq.server.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;

/**
 * @Author kaiqiang
 * @Date 2020/08/08
 */
public class JacksonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper
                // 设置允许序列化空的实体类（否则会抛出异常）
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                // 设置 把java.util.Date, Calendar输出为数字（时间戳）
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                // 设置在遇到未知属性的时候不抛出异常
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                // 强制JSON 空字符串("")转换为null对象值
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                // 设置数字丢失精度问题
                .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
                // 设置没有引号的字段名
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                // 设置允许单引号
                .enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES)
                // 设置接受只有一个元素的数组的反序列化
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    private JacksonUtil(){}

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化Json失败", e);
        }
    }

    public static <T> T toObj(Class<T> clazz, String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json反序列化失败", e);
        }
    }

    public static <T> List<T> toObjList(String jsonStr, Class<T> clazz) {
        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        try {
            return objectMapper.readValue(jsonStr, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json反序列化为list失败", e);
        }
    }
}
