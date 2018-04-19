package com.hanxx.permission.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author hanxx
 * @Date 2018/4/19-16:23
 *  定义用做json返回数据的接口
 */
//get set 方法
@Getter
@Setter
public class JsonData {

    // 返回结果
    private boolean ret;

    // 出现异常返回消息
    private String msg;

    //返回数据
    private Object data;

    /**
     * 只返回是否成功的构造方法
     * @param ret
     */
    public  JsonData (boolean ret){
        this.ret=ret;
    }

    /**
     * 成功的时候不返回任何数据
     * @return
     */
    public  static JsonData success(){
        //成功
        return  new JsonData(true);
    }

    /**
     * 成功的时候返回 数据 与 消息
     * @param o
     * @param msg
     * @return
     */
    public  static JsonData success(Object o, String msg){
        //成功
        JsonData jsonData = new JsonData(true);
        jsonData.data = o;
        jsonData.msg = msg;
        return jsonData;
    }

    /**
     * 成功时只返回数据
     * @param o
     * @return
     */
    public  static JsonData success(Object o){
        //成功
        JsonData jsonData = new JsonData(true);
        jsonData.data = o;
        return jsonData;
    }

    /**
     * 失败时只返回异常消息
     * @param msg
     * @return
     */
    public static JsonData fail(String msg){
        // 失败
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    /**
     * 返回处理后的map
     * @return
     */
    public Map<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("ret",ret);
        map.put("msg",msg);
        map.put("data",data);
        return map;
    }
}
