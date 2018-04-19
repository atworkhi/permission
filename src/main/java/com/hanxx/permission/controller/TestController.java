package com.hanxx.permission.controller;

import com.hanxx.permission.common.JsonData;
import com.hanxx.permission.exception.PermissionException;
import com.hanxx.permission.test.Param;
import com.hanxx.permission.util.BeanValidation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 测试开发环境
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {
    @RequestMapping("/hello.json")  //返回json请求规定加上.json
    @ResponseBody
    public JsonData hello(){
        log.info("Hello permission manager !!! ");
        throw new PermissionException("我是自定义异常");
        //return JsonData.success("Hello, very good");
    }
    @RequestMapping("/validate.json")  //返回json请求规定加上.json
    @ResponseBody
    public JsonData validate(Param param){
        log.info("Hello validate!!! ");
        try {
            Map<String,String> map = BeanValidation.validateObject(param);
            if (map !=null && map.entrySet().size() > 0){
                for (Map.Entry<String,String> entry : map.entrySet()){
                    log.info("{}--{}",entry.getKey(),entry.getValue());
                }
            }
        } catch (Exception e){
        }

        return JsonData.success("Hello, validate");
    }

}
