package com.example.news_portal.mapper;

import com.example.news_portal.model.News;
import com.example.news_portal.services.CategoryService;
import com.example.news_portal.services.UserService;
import com.example.news_portal.web.model.request.UpsertNewsRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private UserService databaseUserService;

    @Autowired
    private CategoryService databaseCategoryService;

    @Override
    public News requestToNews(UpsertNewsRequest request) {
        News news = new News();
        news.setTitle(request.getTitle());
        news.setText(request.getText());
        news.setUser(databaseUserService.findById(request.getUserId()));
        news.setCategory(databaseCategoryService.findByTitle(request.getCategoryName()));
        return news;
    }

    @Override
    public News requestToNews(Long newsId, UpsertNewsRequest request) {
        News news = requestToNews(request);
        news.setId(newsId);
        return news;
    }
}
