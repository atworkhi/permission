package com.hanxx.permission.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hanxx.permission.exception.ParamValidateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import sun.rmi.runtime.Log;

import javax.validation.*;
import java.util.*;

/**
 * @Author hanxx
 * @Date 2018/4/19-17:17
 * 自定义校验工具
 */
@Slf4j
public class BeanValidation {

    //创建校验工厂
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    /**
     * 单个校验
     * @param t
     * @param groups
     * @param <T>
     * @return
     */
    public static <T>Map<String, String> validate(T t,Class... groups){
        Validator validator =validatorFactory.getValidator();
        Set result = validator.validate(t, groups);
        if(result.isEmpty()){
            return Collections.emptyMap();
        }else {
            //发成错误放入 errors
            LinkedHashMap errors = Maps.newLinkedHashMap();
            // 遍历错误
            Iterator iterator = result.iterator();
            while (iterator.hasNext()){
                ConstraintViolation violation = (ConstraintViolation) iterator.next();
                //错误字段 与错误信息
                errors.put(violation.getPropertyPath().toString(),violation.getMessage());
            }
            return errors;
        }
    }

    /**
     * 多个校验
     * @param collection
     * @return
     */
    public static Map<String, String> validateList(Collection<?> collection){
        Preconditions.checkNotNull(collection);
        Iterator iterator = collection.iterator();
        Map errors;
        do{
            if (! iterator.hasNext()){
                return Collections.emptyMap();
            }
            Object object =iterator.next();
            errors=validate(object, new Class[0]);
        }while (errors.isEmpty());
        return errors;
    }

    /**
     * 封装单个校验与多个校验
     * @param first
     * @param objects
     * @return
     */
    public static Map<String, String> validateObject(Object first, Object...objects){
        if (objects !=null && objects.length>0 ){
            return validateList(Lists.asList(first,objects));
        }else {
            return validate(first,new Class[0]);
        }
    }

    public static void validateException(Object object) throws ParamValidateException{
        Map<String,String> map = BeanValidation.validateObject(object);
        if(MapUtils.isNotEmpty(map)){
            throw  new ParamValidateException(map.toString());
        }
    }
}

