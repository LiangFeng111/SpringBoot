package com.spring.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String homePage() {
        return "redirect:/pages/logins.html";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/types")
    public String types(){
        return "types";
    }
    @GetMapping("/archives")
    public String archives(){
        return "archives";
    }
    @GetMapping("/music")
    public String music(){
        return "music";
    }
    @GetMapping("/message")
    public String message(){
        return "message";
    }
    @GetMapping("/friends")
    public String friends(){
        return "friends";
    }
    @GetMapping("/picture")
    public String picture(){
        return "picture";
    }
    @GetMapping("/about")
    public String about(){
        return "about";
    }
      
}
