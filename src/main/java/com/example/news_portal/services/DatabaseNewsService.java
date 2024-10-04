package com.example.news_portal.services;

import com.example.news_portal.exceptions.DublicateNewsTitleException;
import com.example.news_portal.exceptions.EntityNotFoundException;
import com.example.news_portal.mapper.NewsMapper;
import com.example.news_portal.model.Category;
import com.example.news_portal.model.News;
import com.example.news_portal.model.NewsFilter;
import com.example.news_portal.model.User;
import com.example.news_portal.repository.DatabaseCommentRepository;
import com.example.news_portal.repository.DatabaseNewsRepository;
import com.example.news_portal.repository.NewsSpecification;
import com.example.news_portal.utils.BeanUtils;
import com.example.news_portal.web.model.response.NewsResponse_;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseNewsService implements NewsService {

    private final DatabaseNewsRepository newsRepository;

    private final DatabaseUserService databaseUserService;

    private final DatabaseCategoryService databaseCategoryService;

    private final DatabaseCommentRepository commentRepository;

    private final NewsMapper newsMapper;

    @Override
    public List<News> filterBy(NewsFilter filter) {
        return newsRepository.findAll(NewsSpecification.withFilter(filter));
    }

    @Override
    public List<NewsResponse_> findAll() {
        List<News> newsList = newsRepository.findAll();
        List<NewsResponse_> responseList = newsList.stream()
                .map(news -> {
                    NewsResponse_ response = newsMapper.newsToResponse_(news);
                    if (news.getCategory() != null) {
                        response.setCategoryName(news.getCategory().getTitle());
                    }
                    return response;
                }).toList();
        return responseList;
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Новость с ID {0} не найдена!", id)));
    }

    @Override
    public News save(News news) {

        if (!newsRepository.findNewsByTitle(news.getTitle()).isEmpty()) {
            throw new DublicateNewsTitleException("Такое название новости уже есть!");
        }

        User user = databaseUserService.findById(news.getUser().getId());
        news.setUser(user);

        Category category = databaseCategoryService.findById(news.getCategory().getId());
        news.setCategory(category);

        news.setNewsOwner(user.getUsername());

        news.setCommentCount(commentRepository.countByNewsId(news.getId()));

        return newsRepository.save(news);
    }


    @Override
   // @CheckNewsOwner
    public News update(News news) {

        User user = databaseUserService.findById(news.getUser().getId());
        Category category = databaseCategoryService.findById(news.getCategory().getId());

        News existedNews = findById(news.getId());
        BeanUtils.copyNonNullProperties(news, existedNews);
        existedNews.setUser(user);
        existedNews.setCategory(category);

        existedNews.setNewsOwner(user.getUsername());

        existedNews.setCommentCount(commentRepository.countByNewsId(news.getId()));

        return newsRepository.save(existedNews);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public News findByTitle(String title) {
        return newsRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Новость с названием {0} не найдена!", title)));
    }

    @Override
    public String findNewsOwnerById(Long id) {
        return newsRepository.findNewsOwnerById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Новость с ID {0} ещё не создавалась!", id)));
    }

}
