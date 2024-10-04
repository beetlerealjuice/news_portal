package com.example.news_portal.web.controller;

import com.example.news_portal.mapper.CategoryMapper;
import com.example.news_portal.model.Category;
import com.example.news_portal.services.CategoryService;
import com.example.news_portal.web.model.request.UpsertCategoryRequest;
import com.example.news_portal.web.model.response.CategoryListResponse;
import com.example.news_portal.web.model.response.CategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService databaseCategoryService;

    private final CategoryMapper categoryMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CategoryListResponse> findAll(Pageable pageable) {

        CategoryListResponse response = categoryMapper
                .categoryListResponseToCategoryResponseList(
                        databaseCategoryService.findAll(pageable).getContent());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryMapper.categoryToResponse(
                databaseCategoryService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid UpsertCategoryRequest request) {
        Category newCategory = databaseCategoryService.save(categoryMapper.requestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.categoryToResponse(newCategory));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long categoryId,
                                                           @RequestBody @Valid UpsertCategoryRequest request) {
        Category updatedCategory = databaseCategoryService.update(categoryMapper.requestToCategory(categoryId, request));

        return ResponseEntity.ok(categoryMapper.categoryToResponse(updatedCategory));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        databaseCategoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
