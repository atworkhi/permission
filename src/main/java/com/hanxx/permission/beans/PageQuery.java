package com.hanxx.permission.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * @Author:hangx
 * @Date: 2018/4/22 17:41
 * @DESC: 分页查询
 */
public class PageQuery {


    @Getter
    @Setter
    @Min(value = 1,message = "当前页码不合法")
    private int pageNo = 1; // 页码

    @Getter
    @Setter
    @Min(value = 1,message = "每页展示的页码不合法")
    private int pageSize = 10;  // 每页页码

    @Setter
    private int offset; // 偏移量
    public int getOffset(){
        return (pageNo - 1)* pageSize;
    }
}
