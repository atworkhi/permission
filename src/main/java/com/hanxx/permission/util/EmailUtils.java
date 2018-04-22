package com.hanxx.permission.util;

import com.hanxx.permission.beans.Email;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * @Author:hangx
 * @Date: 2018/4/22 22:29
 * @DESC: 发送邮件工具类
 */
@Slf4j
public class EmailUtils {

    public static boolean send(Email email){

        //发件人

        String from = "";
        int port = 25;
        String host = "";
        String pass = "";
        String nickname = "";

        // html格式邮件
        HtmlEmail htmlEmail = new HtmlEmail();
        try {
            // smtp host
            htmlEmail.setHostName(host);
            // 编码
            htmlEmail.setCharset("UTF-8");
            // 遍历收件人
            for (String str : email.getReceivers()){
                // 收件人
                htmlEmail.addTo(str);
            }
            //发送人和昵称
            htmlEmail.setFrom(from,nickname);
            // 邮箱端口
            htmlEmail.setSmtpPort(port);
            //登陆邮件服务器的用户名和密码
            htmlEmail.setAuthentication(from,pass);
            // 邮件主题
            htmlEmail.setSubject(email.getSubject());
            // 发送内容
            htmlEmail.setMsg(email.getMessage());
            // 发送
            htmlEmail.send();
            log.info("{} 发送邮件到 {}",from, StringUtils.join(email.getReceivers(),","));
            return true;
        }catch (EmailException e){
            log.error(from + "发送邮件到" + StringUtils.join(email.getReceivers(), ",") + "失败", e);
            return false;
        }
    }
}
