package com.blog.blog.service;

import com.blog.blog.entity.Post;

import java.util.List;

public interface PostService {

    List<Post> findAll();

    Post findById(int id);

    List<Post> findByUserId(int userId);

    Post save(Post post);

    void deleteById(int id);

}
