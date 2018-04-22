package com.hanxx.permission.beans;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @Author:hangx
 * @Date: 2018/4/22 17:41
 * @DESC: 分页查询返回结果 接收类型为object
 */

@Setter
@Getter
@Builder
@ToString
public class PageResult<T> {

    private List<T> data = Lists.newArrayList();

    private int total = 0;
}
