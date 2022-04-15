package com.spring.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
    private int id;
    private String username;
    private String password;
    private String email;
}
