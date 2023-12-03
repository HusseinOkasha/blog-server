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
    public User findById(int id) {
        Optional<User> result = userRepository.findById(id);
        User user = result.orElseThrow(() -> new RuntimeException("Didn't found user id - " + id));
        return user;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> result = userRepository.findUserByEmail(email);
        User user = result.orElseThrow(()-> new RuntimeException("Couldn't find a user with email:" + email ));
        return user;
    }

    @Override
    public User save(User user) {
        user.setId(0);
        User dbUser = userRepository.save(user);
        return dbUser;
    }

    @Override
    public void deleteById(int id) {
        User user = findById(id);
        userRepository.deleteById(user.getId());
    }
}
