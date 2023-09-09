package com.blog.blog.rest;

import com.blog.blog.entity.User;
import com.blog.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return userService.findById(userId);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        user.setId(0);
        User dbUser = userService.save(user);
        return dbUser;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        User dbUser = userService.save(user);
        return dbUser;
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userService.deleteById(userId);
    }


}
