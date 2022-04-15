package com.spring.demo.utils;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String login= (String) session. getAttribute("token");
        return true;
//        if (login != null){
//            if ("1".equals(login)){
//                return true;
//            }
//        }
//        response.sendRedirect("/pages/logins.html");
//        return false;
    }
}
