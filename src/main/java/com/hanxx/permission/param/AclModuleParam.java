package com.hanxx.permission.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @Author:hangx
 * @Date: 2018/5/2 20:21
 * @DESC: 权限管理模块
 */

@Getter
@Setter
@ToString
public class AclModuleParam {

    private Integer id;

    @NotBlank(message = "权限管理模块名称不能为空")
    @Length(min = 2,max = 20,message = "名称长度为2-20个字符")
    private String name;

    private Integer parentId = 0;

    @NotNull(message = "权限模块的排序规则不能为空")
    private Integer seq;

    @NotNull(message = "模块的属性状态不能为空")
    private Integer status;

    @Length(max = 200, message = "备注长度不能超过200个字符")
    private String remark;

}
