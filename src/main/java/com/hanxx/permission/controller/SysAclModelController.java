package com.hanxx.permission.controller;

import com.hanxx.permission.common.JsonData;
import com.hanxx.permission.param.AclModelParam;
import com.hanxx.permission.service.AclModelService;
import com.hanxx.permission.service.SysTreeService;
import com.hanxx.permission.tree.AclModelLevelDto;
import com.hanxx.permission.tree.DeptLevelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author hanxx
 * @Date 2018/4/24-15:40
 * 权限管理模块controller
 */
@Controller
@RequestMapping("/sys/aclmodel")
@Slf4j
public class SysAclModelController {

    @Autowired
    private AclModelService aclModelService;
    @Autowired
    private SysTreeService treeService;


    //权限管理模块页面
    @RequestMapping("/acl.page")
    public ModelAndView page(){
        return new ModelAndView("acl");
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        //获取列表
        List<AclModelLevelDto> dtoList =treeService.aclModelTree();

        return JsonData.success(dtoList);
    }

    // 新增
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAcl(AclModelParam param){
        aclModelService.save(param);
        return JsonData.success();
    }

    // 修改
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAcl(AclModelParam param){
        aclModelService.update(param);
        return JsonData.success();
    }
}
