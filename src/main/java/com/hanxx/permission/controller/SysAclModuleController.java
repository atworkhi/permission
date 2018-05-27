package com.hanxx.permission.controller;

import com.hanxx.permission.common.JsonData;
import com.hanxx.permission.param.AclModuleParam;
import com.hanxx.permission.param.DeptParam;
import com.hanxx.permission.service.AclModuleService;
import com.hanxx.permission.service.SysTreeService;
import com.hanxx.permission.tree.AclModuleLevelDto;
import com.hanxx.permission.tree.DeptLevelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author:hangx
 * @Date: 2018/5/2 21:19
 * @DESC: 权限管理界面视图
 *
 */
@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {
    @Autowired
    private AclModuleService aclModuleService;
    @Autowired
    private SysTreeService treeService;

    @RequestMapping("/acl.page")
    public ModelAndView page(){
        return new ModelAndView("acl");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData save(AclModuleParam param){
        aclModuleService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        //获取列表
        List<AclModuleLevelDto> aclModuleLevelDtos =treeService.aclModuleTree();
        return JsonData.success(aclModuleLevelDtos);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData update(AclModuleParam param){
        aclModuleService.update(param);
        return JsonData.success();
    }
}
