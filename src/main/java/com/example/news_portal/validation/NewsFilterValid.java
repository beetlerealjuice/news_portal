package com.example.news_portal.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NewsFilterValidator.class)
@Target(ElementType.TYPE) // потому что вешается над классом
@Retention(RetentionPolicy.RUNTIME)
public @interface NewsFilterValid {

    String message() default "Поля фильтрации должны быть указаны!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
