package com.example.news_portal.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsResponse {

    private Long id;

    private String title;

    private String text;

    @JsonProperty("Создатель новости")
    private String newsOwner;

    @JsonProperty("Количество комментариев")
    private Integer commentCount;

    private List<CommentResponse> comments;

}
