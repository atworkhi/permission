package com.hanxx.permission.param;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Member;

/**
 * @Author hanxx
 * @Date 2018/4/24-15:34
 * 权限管理模块
 */
@Getter
@Setter
@ToString
public class AclModelParam {

    // id 更新需要
    private Integer id;

    // 权限管理模块名字
    @NotBlank(message = "权限管理模块名称不能为空")
    @Min( value =  2, message = "权限管理模块名称需要在2-10个字符之间")
    @Max(value = 10, message ="权限管理模块名称需要在2-10个字符之间" )
    private String name;

    // 权限管理模块父目录
    private Integer parentId = 0;

    // 排序权重
    @NotNull(message = "权限模块权重不能为空")
    private Integer seq;

    // 状态
    @NotNull(message = "权限管理模块状态属性不能为空")
    private Integer status;

    // 备注
    @Length(min = 0,max = 100,message = "备注需要在100个字符之内")
    private String remark;
}
