package com.hanxx.permission.common;

import com.hanxx.permission.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Author:hangx
 * Date: 2018/4/19 22:09
 * DESC: http请求监听的工具类
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {
    /**
     * 请求处理之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI().toString();    //请求地址
        Map map =request.getParameterMap();                 //请求参数
        log.info("请求开始之前==》》url:{},params:{}",url, JsonMapper.objString(map));
        return true;
    }

    /**
     * 请求正常结束之后
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String url = request.getRequestURI().toString();    //请求地址
        Map map =request.getParameterMap();                 //请求参数
        log.info("请求正常结束后==》》url:{},params:{}",url, JsonMapper.objString(map));

    }

    /**
     * 正常、异常请求结束都会调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getRequestURI().toString();    //请求地址
        Map map =request.getParameterMap();                 //请求参数
        log.info("请求结束后==》》url:{},params:{}",url, JsonMapper.objString(map));

    }
}
