package com.hanxx.permission.util;


import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

/**
 * Author:hangx
 * Date: 2018/4/19 21:22
 * DESC: json转化工具
 */
@Slf4j
public class JsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    //初始化配置
    static {
        //config
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    // 对象转换为字符串
    public static <T> String objString(T src){
        if (src == null){
            return null;
        }
        try {
            return src instanceof String ? (String) src : objectMapper.writeValueAsString(src);
        } catch (Exception e){
            log.warn("object 转换 string 出错：",e);
            return null;
        }
    }

    //字符串转对象
    public static <T> T stringObj(String str, TypeReference<T> type){
        if (str == null || type == null){
            return null;
        }
        try {
            return (T) (type.getType().equals(String.class) ? str : objectMapper.readValue(str,type));
        }catch (Exception e){
            log.warn("string 转换 object 出错。String:{},type:{},error:{}",str,type.getType(),e.getMessage());
            return null;
        }
    }
}
