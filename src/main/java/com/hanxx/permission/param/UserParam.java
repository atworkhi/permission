package com.hanxx.permission.param;

import com.hanxx.permission.util.PhoneValidation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Author:hangx
 * Date: 2018/4/21 21:15
 * DESC: 用户管理需要的元素
 */
@Getter
@Setter
@ToString
public class UserParam {

    private Integer id; // 用户ID

    @NotBlank(message = "用户名不能为空")
    @Length(min = 2,max = 20,message = "用户名长度不合法")
    private String username;    // 用户姓名

    @NotBlank(message = "用户手机号不能为空")
    @PhoneValidation(message = "手机号码格式不正确")
    private String telephone;   // 用户电话号码

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱不合法")
    private String mail;        // 用户邮箱

    @NotNull(message = "必须为用户选择一个部门")
    private Integer deptId;     // 用户部门用户状态(0.正常1.冻结3.离职)

    @NotNull(message = "用户状态不能不记性设置")
    @Min(value = 0,message = "状态属性不合法")
    @Max(value = 3,message = "状态属性不合法")
    private Integer status;     // 用户状态

    @Length(min = 0,max = 150,message = "备注字符串必须在150个字符之内")
    private String remark;      // 用户备注
}
