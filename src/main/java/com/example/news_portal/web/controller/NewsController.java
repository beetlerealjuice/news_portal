package com.example.news_portal.web.controller;

import com.example.news_portal.aop.Loggable;
import com.example.news_portal.mapper.CommentMapper;
import com.example.news_portal.mapper.NewsMapper;
import com.example.news_portal.model.News;
import com.example.news_portal.model.NewsFilter;
import com.example.news_portal.services.CommentService;
import com.example.news_portal.services.DatabaseUserService;
import com.example.news_portal.services.NewsService;
import com.example.news_portal.web.model.request.UpsertNewsRequest;
import com.example.news_portal.web.model.response.CommentListResponse;
import com.example.news_portal.web.model.response.NewsListResponse;
import com.example.news_portal.web.model.response.NewsResponse;
import com.example.news_portal.web.model.response.NewsResponse_;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService databaseNewsService;

    private final NewsMapper newsMapper;

    private final CommentService databaseCommentService;

    private final CommentMapper commentMapper;

    private final DatabaseUserService userService;

    @GetMapping("/filter")
    public ResponseEntity<NewsListResponse> filterBy(@Valid NewsFilter filter) {
        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(databaseNewsService.filterBy(filter)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<List<NewsResponse_>> findAll() {
        List<NewsResponse_> responseList = databaseNewsService.findAll();

        return ResponseEntity.ok(responseList);

    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentListResponse> findCommentsByNewId(@PathVariable("id") Long newsId) {
        return ResponseEntity.ok(
                commentMapper.commentListToCommentListResponse(databaseCommentService.findByNewsId(newsId))
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsResponse> findById(@PathVariable Long id) {
        News news = databaseNewsService.findById(id);
        NewsResponse response = newsMapper.newsToResponse(news);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsResponse> createNews(@RequestBody @Valid UpsertNewsRequest request,
                                                   @AuthenticationPrincipal UserDetails userDetails) {

        request.setUserId(userService.findByUsername(userDetails.getUsername()).getId());

        News newNews = databaseNewsService.save(newsMapper.requestToNews(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsMapper.newsToResponse(newNews));
    }

    @PutMapping("/{id}")
    @Loggable
    public ResponseEntity<NewsResponse> updateNews(@PathVariable("id") Long newsId,
                                                   @AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody @Valid UpsertNewsRequest request) {

        request.setUserId(userService.findByUsername(userDetails.getUsername()).getId());

        News updatedNews = databaseNewsService.update(newsMapper.requestToNews(newsId, request));

        return ResponseEntity.ok(newsMapper.newsToResponse(updatedNews));
    }

    @DeleteMapping("/{id}")
    @Loggable
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        databaseNewsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }





}
