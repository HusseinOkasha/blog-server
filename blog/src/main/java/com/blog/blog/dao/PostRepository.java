package com.blog.blog.dao;

import com.blog.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<List<Post>> findByUser_id(int userId);
}
