package com.hanxx.permission.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @Author hanxx
 * @Date 2018/4/20-10:18
 * 增删改查部门表中需要的字段
 */
@Getter
@Setter
@ToString
public class DeptParam {

    private Integer id;  //id

    @NotBlank(message = "部门名称不能为空")
    @Length(max = 15, min = 2, message = "部门名称需要在2-15个字之间")
    private String name;    //部门名称

    private  Integer parentId = 0;  //上级部门ID 初始化为顶级菜单

    @NotNull(message = "排序权重不能为空")
    private Integer seq;    //排序

    @Length(max = 150, message = "备注的长度不能超过150个字")
    private String remark;  //备注
}

