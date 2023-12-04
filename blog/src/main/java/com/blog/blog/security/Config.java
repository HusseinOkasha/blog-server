package com.blog.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class Config {

    @Autowired
    AuthenticationProviderService authenticationProviderService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((a)->a.disable())
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.POST, "/api/signup").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults());
        return http.build();
    }
    @Autowired
    public void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProviderService);
    }


}
