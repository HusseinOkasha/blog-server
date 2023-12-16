package com.blog.blog.service;

import com.blog.blog.dao.PostRepository;
import com.blog.blog.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;

    }

    @Override
    public List<Post> findAll() {
        List<Post>posts = postRepository.findAll();
        return posts;
    }

    @Override
    public Optional<Post> findById(int id) {
        Optional<Post> post = postRepository.findById(id);
        return post;
    }

    @Override
    public Optional<Post> save(Post post) {
        return Optional.of(postRepository.save(post));
    }

    @Override
    public List<Post> findByUserId(int userId) {
        List<Post> posts = postRepository.findByUser_id(userId);
        return posts;
    }

    @Override
    public void deleteById(int id) {
        postRepository.deleteById(id);
    }
}
