package com.hanxx.permission.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author:hangx
 * @Date: 2018/4/22 16:34
 * @DESC: admin管理页面
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/index.page")
    public ModelAndView index(){
        return new ModelAndView("admin");
    }
}
