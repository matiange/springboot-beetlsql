package com.matiange.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description: json工具类
 * @date 2020/8/10 14:10
 */
@Slf4j
public class JsonUtil {

    /**
     * 默认jsonMapper处理对象
     */
    private static ObjectMapper defaultMapper;

    public static final org.codehaus.jackson.map.ObjectMapper OM = new org.codehaus.jackson.map.ObjectMapper();
    /**
     * 将object 转换成json toJson
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        try {
            return defaultMapper.writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException("toJson转换错误!", ex);
        }
    }

    /**
     * json串转成 对象
     *
     * @return
     */
    /*public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return OM.readValue(json, clazz);
        } catch (JsonParseException e) {
            log.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }*/
}
