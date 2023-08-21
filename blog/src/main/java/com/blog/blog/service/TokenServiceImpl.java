package com.blog.blog.service;

import com.blog.blog.dao.TokenRepository;
import com.blog.blog.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TokenServiceImpl  implements  TokenService{
    private TokenRepository tokenRepository;
    @Autowired
    TokenServiceImpl(TokenRepository tokenRepository){
        this.tokenRepository = tokenRepository;

    }
    @Override
    public List<Token> findAll(){
        List<Token> tokens = tokenRepository.findAll();
        return tokens;

    }
    @Override
    public Token findById(int id){
        Optional<Token> result = tokenRepository.findById(id);
        Token token = result.orElseThrow(() -> new RuntimeException("Didn't found token id - " + id));
        return token;
    }
    @Override
    public Token save(Token token){
        return tokenRepository.save(token);
    }
    @Override
    public List<Token> findByUserId(int userId){
        Optional<List<Token>> result =  tokenRepository.findByUser_id(userId);
        List<Token> tokens =  result.orElseThrow(()-> new RuntimeException("can't find any tokens for user with id: " + userId));
        return tokens;
    }
    @Override
    public void deleteById(int id){
        tokenRepository.deleteById(id);
    }
}
