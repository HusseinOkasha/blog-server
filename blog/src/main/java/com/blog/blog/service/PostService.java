package com.blog.blog.service;

import com.blog.blog.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> findAll();

    Optional<Post> findById(int id);

    List<Post> findByUserId(int userId);

    Optional<Post> save(Post post);

    void deleteById(int id);

}
