package com.hanxx.permission.filter;

import com.hanxx.permission.common.RequestHolder;
import com.hanxx.permission.model.SysUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:hangx
 * @Date: 2018/4/22 21:42
 * @DESC: 登陆拦截
 */
public class LoginFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 转换成 httpservlet的请求与响应
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取访问url用户
        SysUser user = (SysUser) request.getSession().getAttribute("user");
        //判断是否登陆
        if(user == null){
            String path = "/signin.jsp";
            // 未登陆跳转到登陆界面
            response.sendRedirect(path);
        }
        // 登陆了添加用户到THreadLocal
        RequestHolder.add(user);
        RequestHolder.add(request);
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }
}
