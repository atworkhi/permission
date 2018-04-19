package com.hanxx.permission.controller;

import com.hanxx.permission.common.ApplicationContextHelper;
import com.hanxx.permission.common.JsonData;
import com.hanxx.permission.dao.SysAclModuleMapper;
import com.hanxx.permission.exception.PermissionException;
import com.hanxx.permission.model.SysAclModule;
import com.hanxx.permission.test.Param;
import com.hanxx.permission.util.BeanValidation;
import com.hanxx.permission.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 验证springmvc环境
     * @return
     */
    @RequestMapping("/hi")
    @ResponseBody
    public String hi(){
        return "Hello spring MVC 环境";
    }
    /**
     * 自定义异常
     * @return
     */

    @RequestMapping("/hello.json")  //返回json请求规定加上.json
    @ResponseBody
    public JsonData hello(){
        log.info("Hello permission manager !!! ");
        throw new PermissionException("我是自定义异常");
        //return JsonData.success("Hello, very good");
    }

    /**
     * 自定义验证的验证与验证异常
     * @param param
     * @return
     */
    @RequestMapping("/validate.json")  //返回json请求规定加上.json
    @ResponseBody
    public JsonData validate(Param param){
        log.info("Hello validate!!! ");
        /*try {
            Map<String,String> map = BeanValidation.validateObject(param);
            if (MapUtils.isNotEmpty(map)){
                for (Map.Entry<String,String> entry : map.entrySet()){
                    log.info("{}--{}",entry.getKey(),entry.getValue());
                }
            }
        } catch (Exception e){
        }*/
        // 使用验证异常
        BeanValidation.validateException(param);
        return JsonData.success("Hello, validate");
    }

    /**
     * 验证spring自定义上下文与Json类型工具的转换
     * @return
     */
    @RequestMapping("/application.json")  //返回json请求规定加上.json
    @ResponseBody
    public JsonData application(Param param){
        log.info("Hello permission manager !!! ");
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.objString(module));
        BeanValidation.validateException(param);
        return JsonData.success("spring上下文与json转换类型成功！");
    }



}
