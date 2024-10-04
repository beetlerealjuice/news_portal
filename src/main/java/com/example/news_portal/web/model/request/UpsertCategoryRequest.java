package com.example.news_portal.web.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCategoryRequest {

    @NotBlank(message = "Название категории должно быть заполнено!")
    private String title;

}
