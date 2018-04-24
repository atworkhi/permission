package com.hanxx.permission.controller;

import com.hanxx.permission.common.JsonData;
import com.hanxx.permission.param.AclModelParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author hanxx
 * @Date 2018/4/24-15:40
 * 权限管理模块controller
 */
@Controller
@RequestMapping("/sys/aclmodel")
@Slf4j
public class SysAclModelController {

    //权限管理模块页面
    @RequestMapping("/acl.page")
    public ModelAndView page(){
        return new ModelAndView("acl");
    }
    // 新增
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAcl(AclModelParam param){
        return JsonData.success();
    }

    // 修改
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAcl(AclModelParam param){
        return JsonData.success();
    }
}
