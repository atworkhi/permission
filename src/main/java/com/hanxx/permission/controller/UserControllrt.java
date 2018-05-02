package com.hanxx.permission.controller;

import com.hanxx.permission.model.SysUser;
import com.hanxx.permission.service.UserService;
import com.hanxx.permission.util.MD5Utils;
import com.hanxx.permission.util.PasswordUtil;
import com.sun.deploy.net.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:hangx
 * @Date: 2018/4/22 16:12
 * @DESC: 用户登陆的controller
 */

@Controller
public class UserControllrt {

    @Autowired
    private UserService userService;

    /**
     * 退出登陆
     * @param response
     * @param request
     */
    @RequestMapping("/logout.page")
    public void logout(HttpServletResponse response, HttpServletRequest request) throws IOException {
        // 移除session
        request.getSession().invalidate();
        //跳转页面到登陆
        String path = "signin.jsp";
        response.sendRedirect(path);
    }

    /**
     * 登陆
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response){
        // 从前段获取用户输入的用户名或密码 错误信息 登陆前访问的页面
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String error = "";
        String ret = request.getParameter("ret");
        // 根据关键字(用户名、手机号、email)获取 用户信息
        SysUser user = userService.findKeyWord(username);
        // 验证：
        if (StringUtils.isBlank(username)){
            error = "用户名/邮箱/手机号不能为空";
        }else if (StringUtils.isBlank(password)){
            error = "密码不能为空";
        }else if (user == null) {
            error = "用户不存在";
        }else if (!user.getPassword().equals(MD5Utils.encode(password))){
            error = "用户名或密码错误";
        }else if (user.getStatus() != 0 ){
            error = "用户账号已被冻结,请联系管理员";
        }else {
            // 登陆成功
            // 用户信息放入session
            request.getSession().setAttribute("user",user);
            // 判断登陆是否为其他页面跳转过来，如果是返回以前页面
            if (StringUtils.isNoneBlank(ret)){
                try {
                    response.sendRedirect(ret);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                // 跳转到指定的页面：
                try {
                    response.sendRedirect("/admin/index.page");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 登陆不成功 把信息绑定回去
        request.setAttribute("error",error);
        request.setAttribute("username",username);
        //去往登陆页面
        String path = "signin.jsp";

        if (StringUtils.isNoneBlank(ret)){
            request.setAttribute("ret",ret);
        }

        try {
            request.getRequestDispatcher(path).forward(request,response);
            return;
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
