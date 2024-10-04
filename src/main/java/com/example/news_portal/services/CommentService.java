package com.example.news_portal.services;

import com.example.news_portal.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    Page<Comment> findAll(Pageable pageable);

    Comment findById(Long id);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void deleteById(Long id);

    int countByNewsId(Long newsId);

    List<Comment> findByNewsId(Long newsId);

    String findCommentOwnerById(Long id);
}
