package com.hanxx.permission.controller;

import com.hanxx.permission.beans.PageQuery;
import com.hanxx.permission.beans.PageResult;
import com.hanxx.permission.common.JsonData;
import com.hanxx.permission.model.SysUser;
import com.hanxx.permission.param.UserParam;
import com.hanxx.permission.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author:hangx
 * Date: 2018/4/21 21:14
 * DESC: 用户管理页面controller
 */
@Controller
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController {

    @Autowired
    private UserService userService;

    /**
     * 新增
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData save(UserParam param){
        userService.save(param);
        return JsonData.success();
    }

    /**
     * 修改
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(UserParam param){
        userService.update(param);
        return JsonData.success();
    }

    /**
     * 分页查询用户
     * @param deptId    部门id
     * @param pageQuery 分页查询
     * @return
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData pageList(@RequestParam("deptId") Integer deptId, PageQuery pageQuery){
        PageResult<SysUser> result = userService.getPageByDeptId(deptId,pageQuery);
        return JsonData.success(result);
    }


}
