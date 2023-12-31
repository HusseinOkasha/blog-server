package com.blog.blog.rest;

import com.blog.blog.entity.User;
import com.blog.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@RestController
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    public User findById(@PathVariable int userId) {
        return userService.findById(userId).orElseGet(User::new);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        user.setId(0);
        Optional<User> dbUser = userService.save(user);
        return dbUser.orElseGet(User::new);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        Optional<User> dbUser = userService.save(user);
        return dbUser.orElseGet(User::new);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userService.deleteById(userId);
    }


}
