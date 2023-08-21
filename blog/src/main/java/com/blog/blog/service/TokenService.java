package com.blog.blog.service;

import com.blog.blog.entity.Token;


import java.util.List;

public interface TokenService {
     List<Token> findAll();

     Token findById(int id);

     Token save(Token token);
     List<Token> findByUserId(int userId);
     void deleteById(int id);

}
