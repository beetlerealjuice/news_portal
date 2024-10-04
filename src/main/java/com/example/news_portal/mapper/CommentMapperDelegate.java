package com.example.news_portal.mapper;

import com.example.news_portal.model.Comment;
import com.example.news_portal.services.NewsService;
import com.example.news_portal.services.UserService;
import com.example.news_portal.web.model.request.UpsertCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private UserService databaseUserService;

    @Autowired
    private NewsService databaseNewsService;

    @Override
    public Comment requestToComment(UpsertCommentRequest request) {
        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setUser(databaseUserService.findById(request.getUserId()));
        comment.setNews(databaseNewsService.findById(request.getNewsId()));
        return comment;
    }

    @Override
    public Comment requestToComment(Long commentId, UpsertCommentRequest request) {
        Comment comment = requestToComment(request);
        comment.setId(commentId);
        return comment;
    }
}
