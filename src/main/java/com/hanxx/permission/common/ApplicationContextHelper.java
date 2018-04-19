package com.hanxx.permission.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Author:hangx
 * Date: 2018/4/19 21:45
 * DESC: spring上下文工具
 */
@Component("applicationContextHelper")  //spring管理
public class ApplicationContextHelper implements ApplicationContextAware{

    private static ApplicationContext applicationContext;   //全局的 applicationContext

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    /**
     * 从springbean 上下文中取 class
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T popBean(Class<T> clazz){
        if (applicationContext == null ){
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    /**
     * 从springbean取出 名字 和 class
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T popBean(String name,Class<T> clazz){
        if (applicationContext == null ){
            return null;
        }
        return applicationContext.getBean(name, clazz);
    }

}
