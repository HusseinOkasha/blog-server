package com.blog.blog.config;

import com.blog.blog.util.entityDtoConverters.PostConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConversionConfig {
    @Bean
    public PostConverter postConverter() {
        return new PostConverter();
    }

}
