package com.example.news_portal.repository;

import com.example.news_portal.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatabaseCategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);

    Page<Category> findAll(Pageable pageable);

}
