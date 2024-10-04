package com.example.news_portal.validation;

import com.example.news_portal.model.NewsFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NewsFilterValidator implements ConstraintValidator<NewsFilterValid, NewsFilter> {

    @Override
    public boolean isValid(NewsFilter filter, ConstraintValidatorContext constraintValidatorContext) {

        if (filter.getNewsOwner() == null && filter.getCategoryName() == null) {
            return false;
        }

        return true;
    }
}
