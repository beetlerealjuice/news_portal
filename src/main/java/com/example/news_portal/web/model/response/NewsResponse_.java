package com.example.news_portal.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse_ {

    private Long id;

    private String title;

    @JsonProperty("Название категории")
    private String categoryName;

    @JsonProperty("Создатель новости")
    private String newsOwner;

    @JsonProperty("Количество комментариев")
    private Integer commentCount;

}
