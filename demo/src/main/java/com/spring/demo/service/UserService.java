package com.spring.demo.service;

import com.spring.demo.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    boolean login( String  username,  String password);
    void addUser(User user);
    User findByName(String name);

    User findByEmail(String email);
}
