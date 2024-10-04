package com.example.news_portal.services;

import com.example.news_portal.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<Category> findAll(Pageable pageable);

    Category findById(Long id);

    Category save(Category category);

    Category update(Category category);

    void deleteById(Long id);

    Category findByTitle(String title);


}
