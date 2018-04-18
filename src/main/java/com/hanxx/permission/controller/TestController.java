package com.hanxx.permission.controller;




import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试开发环境
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {
    Log log =LogFactory.getLog(TestController.class);
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        log.info("hello controller");
        return "hello,permission";
    }
}
