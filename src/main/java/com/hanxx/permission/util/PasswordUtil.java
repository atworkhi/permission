package com.hanxx.permission.util;

import java.util.Random;
import java.util.Date;

/**
 * @Author:hangx
 * @Date: 2018/4/21 22:58
 * @DESC: 密码工具类 用来生成随机密码
 */
public class PasswordUtil {

    // 密码的字符
    public final static String[] word = {
            "a","b","c","d","e","f","g","h","j","m","n","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","J","M","N","P","Q","R","S","T","U","V","W","X","Y","Z",

    };
    // 密码数字
    public final static String[] num = {
            "1","2","3","4","5","6","7","8","9","0",
    };
    // 密码随机符号：
    public final static String[] symbol = {
            "!","@","#","$","%","(",")","&"
    };

    /**
     * 随机生成密码
     * @return
     */
    public static String randomPassword(){
        // 定义一个stringbuffer接受随机密码：
        StringBuffer sb = new StringBuffer();
        // 随机密码设置为时间戳 保证每次密码都不一样
        Random random = new Random(new Date().getTime());
        // 随机密码的长度 8-12位
        Integer length = random.nextInt(4)+8;
        int i = 0;
        while (i < length) {
            sb.append(word[random.nextInt(word.length)]);
            i++;
            if (i < length){
                sb.append(num[random.nextInt(num.length)]);
                i++;
            }else {
                break;
            }
            if (i < length){
                sb.append(symbol[random.nextInt(symbol.length)]);
                i++;
            }else {
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 测试随机密码
     * @param args
     */
    public static void main(String[] args){
        System.out.println(randomPassword());
    }
}
