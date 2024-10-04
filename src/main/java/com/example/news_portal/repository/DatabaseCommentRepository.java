package com.example.news_portal.repository;

import com.example.news_portal.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface DatabaseCommentRepository extends JpaRepository<Comment, Long> {

    // Этот метод будет автоматически реализован Spring Data JPA на основе имени метода:
    // Магия!
    // Метод для подсчета комментариев по ID новости

    int countByNewsId(Long newsId);

    List<Comment> findByNewsId(Long newsId);

    @Query(value = "SELECT comment_owner FROM comments WHERE id = :id", nativeQuery = true)
    Optional<String> findCommentOwnerById(Long id);

    Page<Comment> findAll(Pageable pageable);
}
