package com.blog.blog.rest;

import com.blog.blog.entity.Token;
import com.blog.blog.service.TokenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class TokenRestController {
    private TokenService tokenService;

    public TokenRestController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/tokens")
    public List<Token> findAll() {
        return this.tokenService.findAll();
    }

    @GetMapping("/tokens/{tokenId}")
    public Token findById(@PathVariable int tokenId) {
        return this.tokenService.findById(tokenId);
    }

    @PostMapping("/tokens")
    public Token addToken(@RequestBody Token token) {
        token.setId(0);
        Token dbToken = this.tokenService.save(token);
        return dbToken;
    }

    @PutMapping("/tokens")
    public Token updateToken(@RequestBody Token token) {
        Token dbToken = this.tokenService.save(token);
        return dbToken;
    }

    @DeleteMapping("/tokens/{tokenId}")
    public void deleteById(@PathVariable int tokenId) {
        this.tokenService.deleteById(tokenId);
    }

}
