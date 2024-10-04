package com.example.news_portal.web.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCommentRequest {
    
    private Long userId;

    @NotBlank(message = "Текст комментария должен быть указан!")
    private String text;

    @NotNull(message = "ID новости должен быть указан!")
    @Positive(message = "ID новости должен быть больше 0!")
    private Long newsId;

}
