package com.spring.demo.service.impl;

import com.spring.demo.entity.User;
import com.spring.demo.mapper.UserMapper;
import com.spring.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public boolean login(String username, String password) {
        User login = userMapper.login(username, password);
        return login != null;
    }

    @Override
    public void addUser(User user) {
        int id = userMapper.addUser(user);
    }

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }
}
