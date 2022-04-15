package com.spring.demo.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SendEmail {
    private static final String HostName="smtp.qq.com";
    private static final String Sender="sxx1637@qq.com";
    private static final String Charset="utf-8";
    private static final String SenderUserName="腾讯科技";
    private static final String SenderPassword="fihizqahnallcbhi";
    private static final String Title="注册验证码";

    //发送
    public static void sendEmail(String consignee, HttpServletRequest request) throws EmailException {
        int verificationCode = (int) ((Math.random() * 9 + 1) * 100000);//随机数6位
        System.out.println(consignee);
        HtmlEmail email = new HtmlEmail();//创建一个HtmlEmail实例对象
        email.setHostName(HostName);//邮箱的SMTP服务器，一般123邮箱的是smtp.123.com,qq邮箱为smtp.qq.com
        email.setSmtpPort(587);//切换成 587 端口
        email.setSSLOnConnect(true);//将 SSLOnConnect 设置为 true
        email.setCharset(Charset);//设置发送的字符类型
        email.addTo(consignee);//设置收件人
        email.setFrom(Sender,SenderUserName);//发送人的邮箱为自己的，用户名可以随便填
        email.setAuthentication(Sender,SenderPassword);//设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)
        email.setSubject(Title);//设置发送主题
        email.setMsg("尊敬的用户您好,您本次注册的验证码是:"+"<h1>"+verificationCode+"</h1>");//设置发送内容
        email.send();//进行发送
        System.out.println(verificationCode+"生成的验证码");
        request.getSession().setAttribute("emailCode",verificationCode);
        System.out.println(consignee);
        request.getSession().setAttribute("emailName",consignee);
    }

    public static void main(String[] args) {
        HttpServletRequest request = null;
        try {
            sendEmail("2629337569@qq.com",request);
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
