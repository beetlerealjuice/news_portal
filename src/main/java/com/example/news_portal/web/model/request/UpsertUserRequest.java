package com.example.news_portal.web.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertUserRequest {

    @NotBlank(message = "Имя пользователя должно быть заполнено!")
    private String username;

    @NotBlank(message = "Пароль должен быть заполнен!")
    private String password;

}
