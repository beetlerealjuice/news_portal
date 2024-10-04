package com.example.news_portal.repository;

import com.example.news_portal.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DatabaseNewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

    Optional<News> findByTitle(String title);

    @Query(value = "SELECT news_owner FROM news WHERE id = :id", nativeQuery = true)
    Optional<String> findNewsOwnerById(Long id);

    @Query(value = "SELECT title FROM news WHERE title = :title", nativeQuery = true)
    List<Optional<String>> findNewsByTitle(String title);




}
