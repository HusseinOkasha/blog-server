package com.blog.blog.service;

import com.blog.blog.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder encoder;
    private final JwtDecoder jwtDecoder;
    public TokenService(JwtEncoder encoder, JwtDecoder jwtDecoder) {
        this.encoder = encoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


    public Jwt getToken(String authorizationHeader){
        String encodedToken = extractToken(authorizationHeader);
        Jwt decodedToken = decodeToken(encodedToken);
        return  decodedToken ;
    }
    public String extractToken(String authorizationHeader){
        // authorizationHeader is like: Bearer token
        String encodedToken = authorizationHeader.substring(7);
        return encodedToken;

    }
    public Jwt decodeToken(String encodedToken){
        Jwt decodedToken = jwtDecoder.decode(encodedToken);
        return decodedToken ;
    }

}