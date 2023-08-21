package com.blog.blog.service;

import com.blog.blog.dao.PostRepository;
import com.blog.blog.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;
    @Autowired
    PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;

    }
    @Override
    public List<Post> findAll(){
        List<Post> posts = postRepository.findAll();
        return posts;

    }
    @Override
    public Post findById(int id){
        Optional<Post> result = postRepository.findById(id);
        Post post = result.orElseThrow(() -> new RuntimeException("Didn't found post id - " + id));
        return post;
    }
    @Override
    public Post save(Post post){
        return postRepository.save(post);
    }
    @Override
    public List<Post> findByUserId (int userId){
        Optional<List<Post>> result = postRepository.findByUser_id(userId);
        List<Post> posts = result.orElseThrow(()-> new RuntimeException("Can't find any post for user id: " + userId));
        return posts;
    }
    @Override
    public void deleteById(int id){
        postRepository.deleteById(id);
    }
}
