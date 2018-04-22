package com.hanxx.permission.common;

import com.hanxx.permission.model.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:hangx
 * @Date: 2018/4/22 21:37
 * @DESC: ThreadLoacl
 */
public class RequestHolder {
    // 初始化用户
    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<>();
    // 初始化请求
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    // 把用户添加到ThreadLocal
    public static void add(SysUser user){
        userHolder.set(user);
    }

    // 请求添加到THreadLocal
    public static void add(HttpServletRequest request){
        requestHolder.set(request);
    }

    // 获取用户
    public static SysUser getCurrentUser(){
        return userHolder.get();
    }

    // 获取请求
    public static HttpServletRequest getCurrentRequest(){
        return requestHolder.get();
    }

    // 移除进程
    public static void remove(){
        userHolder.remove();
        requestHolder.remove();
    }

}
