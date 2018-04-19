package com.hanxx.permission.common;

import com.hanxx.permission.exception.PermissionException;
import com.hanxx.permission.exception.ParamValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author hanxx
 * @Date 2018/4/19-16:35
 * 异常处理类
 */
@Slf4j
public class SpringException implements HandlerExceptionResolver{
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
       String url = request.getRequestURL().toString();  //获取请求url
        ModelAndView mv;    //视图
        String errorDefault = "系统异常";

        // .json 请求  .page页面请求
        if(url.endsWith(".json")){  //.json结尾为json请求
            //如果属于我们自定义的异常
            if (e instanceof PermissionException || e instanceof ParamValidateException){
                JsonData result = JsonData.fail(e.getMessage());
                //返回视图为map类型的json springMvc配置文件中定义为 jsonView
                mv = new ModelAndView("jsonView",result.toMap());
            } else {
                log.error("未知json异常 ,URL:"+url , e); //记录异常日志
                JsonData result = JsonData.fail(errorDefault);
                mv = new ModelAndView("jsonView",result.toMap());
            }
        }else if (url.endsWith(".page")) {     //页面返回
            log.error("未知page异常,URL:"+url , e); //记录异常日志
            JsonData result = JsonData.fail(errorDefault);
            // 页面会获取 View 中的exception页面处理
            mv = new ModelAndView("exception",result.toMap());
        }else {
            JsonData result = JsonData.fail(errorDefault);
            mv = new ModelAndView("jsonView",result.toMap());
        }
        return mv;
    }
}
