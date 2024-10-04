package com.example.news_portal.services;

import com.example.news_portal.exceptions.EntityNotFoundException;
import com.example.news_portal.model.Comment;
import com.example.news_portal.model.News;
import com.example.news_portal.model.User;
import com.example.news_portal.repository.DatabaseCommentRepository;
import com.example.news_portal.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseCommentService implements CommentService {

    private final DatabaseCommentRepository commentRepository;

    private final DatabaseNewsService databaseNewsService;

    private final DatabaseUserService databaseUserService;

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Комментарий с ID {0} не найден!", id)));
    }

    @Override
    public Comment save(Comment comment) {

        User user = databaseUserService.findById(comment.getUser().getId());
        comment.setUser(user);

        News news = databaseNewsService.findById(comment.getNews().getId());
        comment.setNews(news);

        comment.setCreatedAt(BeanUtils.instantToFormattedString(Instant.now()));

        comment.setCommentOwner(user.getUsername());

        news.setCommentCount(news.getCommentCount() + 1);

        return commentRepository.save(comment);
    }

    @Override
   // @CheckCommentOwner
    public Comment update(Comment comment) {

        News news = databaseNewsService.findById(comment.getNews().getId());
        User user = databaseUserService.findById(comment.getUser().getId());

        Comment existedComment = findById(comment.getId());

        BeanUtils.copyNonNullProperties(comment, existedComment);
        existedComment.setNews(news);
        existedComment.setUser(user);
        existedComment.setCommentOwner(user.getUsername());

        existedComment.setUpdatedAt(BeanUtils.instantToFormattedString(Instant.now()));

        return commentRepository.save(existedComment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public int countByNewsId(Long newsId) {
        return commentRepository.countByNewsId(newsId);
    }

    @Override
    public List<Comment> findByNewsId(Long newsId) {
        return commentRepository.findByNewsId(newsId);
    }

    @Override
    public String findCommentOwnerById(Long id) {
        return commentRepository.findCommentOwnerById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Комментарий с ID {0} ещё не создавался!", id)));
    }


}
