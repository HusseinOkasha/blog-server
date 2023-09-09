package com.blog.blog.service;

import com.blog.blog.entity.DraftPost;

import java.util.List;
import java.util.Optional;

public interface DraftPostService {

    List<DraftPost> findAll();

    DraftPost findById(int id);

    DraftPost save(DraftPost draftPost);

    List<DraftPost> findByUserId(int userId);

    void deleteById(int id);

}
