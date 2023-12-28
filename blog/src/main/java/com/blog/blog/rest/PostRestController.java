package com.blog.blog.rest;

import com.blog.blog.dto.PostDto;
import com.blog.blog.entity.Post;
import com.blog.blog.entity.User;
import com.blog.blog.service.PostService;
import com.blog.blog.service.TokenService;
import com.blog.blog.service.UserService;
import com.blog.blog.util.entityDtoConverters.PostConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RequestMapping("api")
@RestController
public class PostRestController {

    private PostService postService;
    private TokenService tokenService;
    private UserService userService;
    private PostConverter postConverter;

    @Autowired
    public PostRestController(PostService postService, TokenService tokenService, UserService userService,
                              PostConverter postConverter) {
        this.postService = postService;
        this.tokenService = tokenService;
        this.userService = userService;
        this.postConverter = postConverter;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> findAll(@RequestHeader String authorization) {
        // Get the user trying to add a post using the jwt token
        Optional<User> user = tokenService.getUser(authorization);

        // Get the posts of the user specified in the jwt token.
        List<Post> userPosts = user.map((User::getPosts)).orElseGet(ArrayList::new);

        // convert post entity to postDTO
        List<PostDto> userPostsDto = userPosts.stream().map((post)-> postConverter.convertPostEntityToPostDTO(post))
                .toList();

        // in case that the user in the token is not found in the database.
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userPostsDto);
        }
        return ResponseEntity.ok(userPostsDto);
    }

    @PostMapping("/posts")
    public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto, @RequestHeader String authorization) {
        Post post = postConverter.convertPostDtoToPostEntity(postDto);

        // to let the database set the id
        post.setId(0);

        // handling case of missing post body or empty post body
        // 1) {}.
        // 2) {"body": ""}.
        // note: handling case of empty request body is done by spring.
        if(post.getBody().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PostDto());
        }

        // Get the user trying to add a post using the jwt token
        Optional<User> user = tokenService.getUser(authorization);

        return user
                .map(value -> {
                    post.setUser(value);
                    Optional<Post> dbPost = postService.save(post);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(postConverter.convertPostEntityToPostDTO(
                                    // in case of failing to save the post to the database
                                    dbPost.orElseGet(Post::new)
                            ));
                })
                // if the user embedded in the jwt token doesn't exist
                .orElseGet(()-> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new PostDto()));
    }

    @PutMapping("/posts")
    public ResponseEntity<String> updatePost(@RequestBody PostDto postDto , @RequestHeader String authorization) {
        // Get the user trying to update the post using the jwt token
        Optional<User> reqUser = tokenService.getUser(authorization);

        // Check if there is a post with provided id in the request body
        Optional<Post> result = postService.findById(postDto.id());
        if(result.isEmpty()){ return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("The post you are trying to" +
                " update doesn't exist");}

        Post dbPost = result.get();

        // Get the user who originally created that post we are trying to update
        User dbPostUser = dbPost.getUser();

        // Check the case if a user is trying to update a post which belongs to another user
        if(reqUser.isEmpty() || dbPostUser != reqUser.get()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Actually update the post
        dbPost.setBody(postDto.body());
        Optional<Post> dbPostAfterUpdate = postService.save(dbPost);

        return ResponseEntity.ok("Post updated successfully");
    }


    @DeleteMapping("/posts")
    public ResponseEntity<String> deletePostById(@RequestBody PostDto postDto, @RequestHeader String authorization) {
        // Get the user trying to delete the post using the jwt token
        Optional<User> reqUser = tokenService.getUser(authorization);

        // Check if there is a post with provided id in the request body
        Optional<Post> result = postService.findById(postDto.id());
        if(result.isEmpty()){ return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("The post you are trying to" +
                " delete doesn't exist");}

        Post dbPost = result.get();

        // Check the case if a user is trying to delete a post which belongs to another user
        User dbUser = dbPost.getUser();
        if(reqUser.isEmpty() || reqUser.get()!= dbUser){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        postService.deleteById(dbPost.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully");
    }



}
