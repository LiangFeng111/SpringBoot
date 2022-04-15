package com.spring.demo;

import com.alibaba.fastjson.JSONObject;
import com.spring.demo.entity.User;
import com.spring.demo.utils.JavaWebToken;
import com.spring.demo.utils.MD5Util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Mytest {
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        Map<String, Object> claims = new HashMap<String,Object>();
//        //模拟添加session中的用户数据
//        claims.put("tk","123456789");
//        //转成token
//        String myToken = JavaWebToken.createJavaWebToken(claims);
//        System.out.println("我的token数据：" + myToken);
//        //token转化为原数据
//        Map<String, Object> myTokenMap = JavaWebToken.parserJavaWebToken(myToken);
//        System.out.println("token转化为Map：" + myTokenMap);
//        assert myTokenMap != null;
//        Object login = myTokenMap.get("tk");
//        System.out.println(login);
//    }

    public static void main(String[] args) {
        String pass = MD5Util.convertMD5("dd");
        System.out.println(pass);
    }
}
