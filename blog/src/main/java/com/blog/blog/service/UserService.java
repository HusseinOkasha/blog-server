package com.blog.blog.service;

import com.blog.blog.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    User findByEmail(String email);

    User save(User user);

    void deleteById(int id);

}
