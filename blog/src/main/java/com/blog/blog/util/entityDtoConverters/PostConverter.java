package com.blog.blog.util.entityDtoConverters;

import com.blog.blog.dto.PostDto;
import com.blog.blog.entity.Post;
import com.blog.blog.entity.User;
import com.blog.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public final class PostConverter {
    private PostService postService;
    public PostConverter(){}
    @Autowired
    public PostConverter(PostService postService){

        this.postService = postService;
    }

    public Post convertPostDtoToPostEntity(PostDto postDto){
        Post post = new Post(postDto.body(), postDto.createdAt(), postDto.updatedAt(), new User());
        String body = postDto.body() == null ? "" : postDto.body();
        post.setBody(body);
        post.setId(postDto.id());
        return post;
    }


    public PostDto convertPostEntityToPostDTO(Post post){
        PostDto postDto = new PostDto(post.getId(), post.getBody(), post.getCreatedAt(), post.getUpdatedAt() ,post.getUser().getEmail());
        return postDto;
    }


}
