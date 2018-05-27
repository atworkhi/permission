package com.hanxx.permission.controller;

import com.hanxx.permission.beans.PageQuery;
import com.hanxx.permission.beans.PageResult;
import com.hanxx.permission.common.JsonData;
import com.hanxx.permission.model.SysAcl;
import com.hanxx.permission.param.AclParam;
import com.hanxx.permission.service.AclService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author:hangx
 * @Date: 2018/5/27 17:40
 * @DESC: 权限点controller
 */
@Controller
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {

    @Autowired
    private AclService aclService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData save(AclParam param){
        aclService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData update(AclParam param){
        aclService.update(param);
        return JsonData.success();
    }
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery page){
        PageResult<SysAcl> pageResult = aclService.getPageByAclModuleId(aclModuleId,page);
        return JsonData.success(pageResult);
    }


}

