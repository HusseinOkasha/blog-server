package com.blog.blog.rest;

import com.blog.blog.entity.Post;
import com.blog.blog.entity.User;
import com.blog.blog.service.PostService;
import com.blog.blog.service.TokenService;
import com.blog.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("api")
@RestController
public class PostRestController {

    private PostService postService;
    private TokenService tokenService;
    private UserService userService;

    @Autowired
    public PostRestController(PostService postService, TokenService tokenService, UserService userService) {
        this.postService = postService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> findAll(@RequestHeader String authorization) {
        // Get the user trying to add a post using the jwt token
        Optional<User> user = tokenService.getUser(authorization);

        return user.map(value -> ResponseEntity.ok(value.getPosts()))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/posts")
    public ResponseEntity<String> addPost(@RequestBody Post post, @RequestHeader String authorization) {
        post.setId(0);
        // Get the user trying to add a post using the jwt token
        Optional<User> user = tokenService.getUser(authorization);
        return user.map(value -> {
                post.setUser(user.get());
                Optional<Post> result = postService.save(post);
                return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
            }).orElseGet(()-> ResponseEntity.status(401).body("Failed to create the post perhaps it's a credentials problem"));
    }


    @PutMapping("/posts")
    public ResponseEntity<String> updatePost(@RequestBody Post post , @RequestHeader String authorization) {
        // Get the user trying to update the post using the jwt token
        Optional<User> reqUser = tokenService.getUser(authorization);

        // Check if there is a post with provided id in the request body
        Optional<Post> result = postService.findById(post.getId());
        if(result.isEmpty()){ return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("The post you are trying to" +
                " update doesn't exist");}

        Post dbPost = result.get();

        // Get the user who originally created that post we are trying to update
        User dbPostUser = dbPost.getUser();

        // Check the case if a user is trying to update a post which belongs to another user
        if(reqUser.isEmpty() || dbPostUser != reqUser.get()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Actually update the post
        dbPost.setBody(post.getBody());
        Optional<Post> dbPostAfterUpdate = postService.save(dbPost);

        return ResponseEntity.ok("Post updated successfully");
    }


    @DeleteMapping("/posts")
    public ResponseEntity<String> deletePostById(@RequestBody Post post, @RequestHeader String authorization) {
        // Get the user trying to delete the post using the jwt token
        Optional<User> reqUser = tokenService.getUser(authorization);

        // Check if there is a post with provided id in the request body
        Optional<Post> result = postService.findById(post.getId());
        if(result.isEmpty()){ return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("The post you are trying to" +
                " delete doesn't exist");}

        Post dbPost = result.get();

        // Check the case if a user is trying to delete a post which belongs to another user
        User dbUser = dbPost.getUser();
        if(reqUser.isEmpty() || reqUser.get()!= dbUser){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        postService.deleteById(post.getId());

        return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully");
    }



}
