package com.blog.blog.dao;

import com.blog.blog.entity.DraftPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DraftPostRepository extends JpaRepository<DraftPost, Integer> {
    Optional<List<DraftPost>> findByUser_id(int userId);
}
