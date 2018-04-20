package com.hanxx.permission.controller;

import com.hanxx.permission.common.JsonData;
import com.hanxx.permission.param.DeptParam;
import com.hanxx.permission.service.DeptService;
import com.hanxx.permission.service.SysTreeService;
import com.hanxx.permission.tree.DeptLevelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author hanxx
 * @Date 2018/4/20-10:26
 * 部门的controller
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Autowired
    private DeptService deptService;
    @Autowired
    private SysTreeService treeService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param){
        deptService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        //获取列表
        List<DeptLevelDto> dtoList =treeService.deptTree();

        return JsonData.success(dtoList);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param){
        deptService.update(param);
        return JsonData.success();
    }

}
