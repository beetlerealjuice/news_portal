package com.example.news_portal.web.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertNewsRequest {

//    @NotNull(message = "ID пользователя должно быть указано!")
//    @Positive(message = "ID пользователя должно быть больше 0!")
    private Long userId;

    @NotBlank(message = "Название новости должно быть указано!")
    private String title;

    private String text;

    @NotBlank(message = "Название категории должно быть заполнено!")
    private String categoryName;


}
