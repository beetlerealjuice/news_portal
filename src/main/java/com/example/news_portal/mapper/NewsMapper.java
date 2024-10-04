package com.example.news_portal.mapper;

import com.example.news_portal.model.News;
import com.example.news_portal.web.model.response.NewsListResponse;
import com.example.news_portal.web.model.response.NewsResponse;
import com.example.news_portal.web.model.request.UpsertNewsRequest;
import com.example.news_portal.web.model.response.NewsResponse_;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {

    News requestToNews(UpsertNewsRequest request);

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, UpsertNewsRequest request);

    NewsResponse newsToResponse(News news);
    NewsResponse_ newsToResponse_(News news);

    List<NewsResponse> newsListToResponseList(List<News> newsList);

    List<NewsResponse_> newsListToResponseList_(List<News> newsList);

    default NewsListResponse newsListToNewsListResponse(List<News> newsList) {
        NewsListResponse response = new NewsListResponse();
        response.setNewsList(newsListToResponseList(newsList));

        return response;
    }
}
