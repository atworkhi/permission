package com.hanxx.permission.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author:hangx
 * @Date: 2018/5/27 17:43
 * @DESC: 更改的类
 */
@Getter
@Setter
@ToString
public class AclParam {

    private Integer id;

    @NotBlank(message = "名称不能为空！")
    @Length(min = 2, max = 20, message = "名称只能在2-20个字之间")
    private String name;

    @NotNull(message = "权限模块不能为空！")
    private Integer aclModuleId;

    @Length(min = 6, max = 100, message = "权限点的URL在6-100个字符之间")
    private String url;

    @NotNull(message = "必须指定权限点的类型！")
    private Integer type;

    @NotNull(message = "状态不合法!")
    private Integer status;

    @NotNull(message = "权限点的权重不能为空！")
    private Integer seq;

    @Length(min = 0, max = 200, message = "全县点的备注长度不能大于200")
    private String remark;

}
