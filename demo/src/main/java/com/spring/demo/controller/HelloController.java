package com.spring.demo.controller;

import com.spring.demo.entity.User;
import com.spring.demo.service.UserService;
import com.spring.demo.utils.JavaWebToken;
import com.spring.demo.utils.MD5Util;
import com.spring.demo.utils.SendEmail;
import com.spring.demo.utils.VerifyCodeUtils;
import javafx.geometry.Pos;
import org.apache.commons.mail.EmailException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class HelloController {
    @Autowired
    UserService userService;

    //登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> login(@RequestParam("username") String username, @RequestParam("password")String password,@RequestParam("loginCode") String loginCode, HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        HashMap<String, Object> map = new HashMap<>();
        String sloginCode = String.valueOf(session.getAttribute("loginCode"));
        //判断两字符串忽略大小写是否相等
        if (!sloginCode.equalsIgnoreCase(loginCode)){
            map.put("login","-1");

        }else {
            String pass = MD5Util.string2MD5(password);
            boolean login = userService.login(username, pass);
            if (login){
                HashMap<String, Object> claims = new HashMap<>();
                claims.put("token",username);
                String myToken = JavaWebToken.createJavaWebToken(claims);
                session.setAttribute("token",myToken);
                response.setHeader("Access-Control-Expose-Headers", "TOKEN_NAME");
                response.setHeader("TOKEN", myToken);
            }
            map.put("login",login);
        }
        return map;
    }

    //清除登录
    @RequestMapping("/nologin")
    @ResponseBody
    public String  nologin(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("token","");
        response.setHeader("CONTENTPATH", "pages/logins.html");
        return "ok";
    }

    //登录验证码
    @RequestMapping(value = "/loginCode")
    public void loginCode( HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        //生成验证码
        ServletOutputStream os = response.getOutputStream();
        String loginCode = VerifyCodeUtils.outputVerifyImage(100,50,os,4);
        session.setAttribute("loginCode",loginCode);
    }


    //发送验证码
    @RequestMapping("/sendEmail")
    @ResponseBody
    public void email(@RequestParam("email") String emailName, HttpServletRequest request) throws EmailException {
        SendEmail.sendEmail(emailName,request);
    }



    //注册验证
    @RequestMapping("/isEmail")
    @ResponseBody
    public Map<String ,Object>  isEmail(@RequestParam("code") int code,@RequestParam("emailName") String emailNamea, HttpServletRequest request){
        HttpSession session = request.getSession();
        Map<String ,Object> map = new HashMap<>();
        Object emailCode = session.getAttribute("emailCode");
        Object emailName = session.getAttribute("emailName");
        if (emailCode !=null && emailName != null){
            int scode = (int) emailCode;
            String emailNames= (String) emailName;
            if (code ==scode && emailNames.equals(emailNamea)) {
                map.put("verify","1");
            }
        } else {
            map.put("verify","-1");
        }
        return map;
    }

    //注册
    @RequestMapping(value = "/register" ,method = RequestMethod.POST)
    public String  register(@RequestBody List<User> userList,HttpServletRequest request){
        User user = userList.get(0);
        user.setPassword(MD5Util.string2MD5(user.getPassword()));
        userService.addUser(user);
        return "forward:/pages/login.html";
    }


    //用户名判断
    @RequestMapping(value = "/findByName" ,method = RequestMethod.POST)
    @ResponseBody
    public String  findByName(@Param("name") String name){
        User byName = userService.findByName(name);
        if (byName != null){
            return "-1";
        }
        return "1";
    }

    //邮箱判断
    @RequestMapping(value = "/findByEmail" ,method = RequestMethod.POST)
    @ResponseBody
    public String  findByEmail(@Param("email") String email){
        User byName = userService.findByEmail(email);
        if (byName != null){
            return "-1";
        }
        return "1";
    }
}
