package com.blog.blog.service;

import com.blog.blog.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

    Optional<User> save(User user);

    void deleteById(int id);

}
