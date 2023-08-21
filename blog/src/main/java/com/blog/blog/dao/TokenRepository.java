package com.blog.blog.dao;

import com.blog.blog.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<List<Token>> findByUser_id(int userId);
}
