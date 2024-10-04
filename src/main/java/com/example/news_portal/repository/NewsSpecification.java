package com.example.news_portal.repository;

import com.example.news_portal.model.News;
import com.example.news_portal.model.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {

    static Specification<News> withFilter(NewsFilter filter) {
        return Specification.where(byCategoryName(filter.getCategoryName()))
                .and(byNewsOwner(filter.getNewsOwner()));
    }

    static Specification<News> byCategoryName(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            if (categoryName == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("category").get("title"), categoryName);
        };
    }

    static Specification<News> byNewsOwner(String newsOwner) {
        return (root, query, criteriaBuilder) -> {
            if (newsOwner == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("newsOwner"), newsOwner);
        };
    }

}
