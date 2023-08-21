package com.blog.blog.service;

import com.blog.blog.dao.DraftPostRepository;
import com.blog.blog.entity.DraftPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DraftPostServiceImpl  implements  DraftPostService{
    private DraftPostRepository draftPostRepository;
    @Autowired
    DraftPostServiceImpl(DraftPostRepository draftPostRepository){
        this.draftPostRepository = draftPostRepository;

    }
    @Override
    public List<DraftPost> findAll(){
        List<DraftPost> draftPosts = draftPostRepository.findAll();
        return draftPosts;

    }
    @Override
    public DraftPost findById(int id){
        Optional<DraftPost> result = draftPostRepository.findById(id);
        DraftPost draftPost = result.orElseThrow(() -> new RuntimeException("Didn't found draftPost id - " + id));
        return draftPost;
    }
    @Override
    public DraftPost save(DraftPost draftPost){
        return draftPostRepository.save(draftPost);
    }
    @Override
    public List<DraftPost> findByUserId(int userId){
        Optional<List<DraftPost>> result = draftPostRepository.findByUser_id(userId);
        List<DraftPost> draftPosts = result.orElseThrow(()-> new RuntimeException("can't find any draft post for user id: " + userId));
        return  draftPosts;
    }
    @Override
    public void deleteById(int id){
        draftPostRepository.deleteById(id);
    }
}
