package com.spring.demo.mapper;

import com.spring.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> findAll();
    User findByid(@Param("id") int id);
    User login(@Param("username") String  username, @Param("password") String password);
    int addUser(User user);
    User findByName(@Param("username") String username);

    User findByEmail(@Param("email")String email);
}
