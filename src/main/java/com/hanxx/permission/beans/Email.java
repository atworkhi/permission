package com.hanxx.permission.beans;

import lombok.*;

import java.util.Set;

/**
 * @Author:hangx
 * @Date: 2018/4/22 22:27
 * @DESC: 发送邮件的
 */

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class Email {

    // 邮件主题
    private String subject;

    // 邮件内容
    private String message;
    // 收件人
    private Set<String> receivers;
}
