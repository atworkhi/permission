package com.hanxx.permission.test;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 校验测试
 * @Author hanxx
 * @Date 2018/4/19-17:36
 */
@Getter
@Setter
public class Param {

    @NotBlank
    private String msg;
    @NotNull
    private Integer id;
}
