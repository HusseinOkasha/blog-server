package com.blog.blog.rest;

import com.blog.blog.entity.Post;
import com.blog.blog.entity.User;
import com.blog.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@RestController
public class PostRestController {

    private PostService postService;

    @Autowired
    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> findAll() {
        return postService.findAll();
    }

    @GetMapping("/posts/{postId}")
    public Post findById(@PathVariable int postId) {
        return postService.findById(postId);
    }

    @PostMapping("/posts")
    public Post addPost(@RequestBody Post post) {
        post.setId(0);
        Post dbPost = postService.save(post);
        return dbPost;
    }

    @PutMapping("/posts")
    public Post updatePost(@RequestBody Post post) {
        Post dbPost = postService.save(post);
        return dbPost;
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePostById(@PathVariable int postId) {
        postService.deleteById(postId);
    }

}
