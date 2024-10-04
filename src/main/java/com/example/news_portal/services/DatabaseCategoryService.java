package com.example.news_portal.services;

import com.example.news_portal.exceptions.EntityNotFoundException;
import com.example.news_portal.model.Category;
import com.example.news_portal.repository.DatabaseCategoryRepository;
import com.example.news_portal.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class DatabaseCategoryService implements CategoryService {

    private final DatabaseCategoryRepository categoryRepository;


    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Категории с ID {0} не найдено!", id)));
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {

        Category existedCategory = findById(category.getId());

        BeanUtils.copyNonNullProperties(category, existedCategory);

        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public Category findByTitle(String title) {
        return categoryRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Категории с названием {0} не найдено!", title)));
    }


}
