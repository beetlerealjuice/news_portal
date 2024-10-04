package com.example.news_portal.mapper;

import com.example.news_portal.model.Category;
import com.example.news_portal.web.model.request.UpsertCategoryRequest;
import com.example.news_portal.web.model.response.CategoryListResponse;
import com.example.news_portal.web.model.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsMapper.class})
public interface CategoryMapper {

    Category requestToCategory(UpsertCategoryRequest request);

    @Mapping(source = "categoryId", target = "id")
    Category requestToCategory(Long categoryId, UpsertCategoryRequest request);

    CategoryResponse categoryToResponse(Category category);

    default CategoryListResponse categoryListResponseToCategoryResponseList(List<Category> categories) {
        CategoryListResponse response = new CategoryListResponse();

        response.setCategories(categories.stream()
                .map(this::categoryToResponse)
                .toList());
        return response;
    }

}
