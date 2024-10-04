package com.example.news_portal.services;

import com.example.news_portal.model.News;
import com.example.news_portal.model.NewsFilter;
import com.example.news_portal.web.model.response.NewsResponse_;

import java.util.List;

public interface NewsService {

    List<News> filterBy(NewsFilter filter);

    List<NewsResponse_> findAll();

    News findById(Long id);

    News save(News news);

    News update(News news);

    void deleteById(Long id);

    News findByTitle(String title);

    String findNewsOwnerById(Long id);
}
