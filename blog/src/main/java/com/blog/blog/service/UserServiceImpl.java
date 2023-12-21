package com.blog.blog.service;

import com.blog.blog.dao.UserRepository;
import com.blog.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;

    }

    @Override
    public Optional<User> findById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        return user;
    }

    @Override
    public Optional<User> save(User user) {
        user.setId(0);
        return Optional.of(userRepository.save(user));
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}
