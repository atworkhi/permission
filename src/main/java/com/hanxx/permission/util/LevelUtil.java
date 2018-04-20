package com.hanxx.permission.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author hanxx
 * @Date 2018/4/20-10:37
 * Level 工具类
 */
public class LevelUtil {

    // 层级的分隔符
    public final static String SEPARATOR=".";
    // level的层数开始
    public final static String ROOT="0";

    /**
     * 层级的计算规则
     * 首层：0
     * 二层：0.1       0.2
     * 三层：0.1.1     0.2.1
     * @param parentLevel
     * @param parentId
     * @return
     */
    public static String calLevel(String parentLevel, int parentId){
            // 引入apache-commons-lang3工具类
            if(StringUtils.isBlank(parentLevel)) {  //判断是否为首层
                return ROOT;
            }else {
                return StringUtils.join(parentLevel,SEPARATOR,parentId);
            }
    }
}
