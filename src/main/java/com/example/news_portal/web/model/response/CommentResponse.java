package com.example.news_portal.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {

    private Long id;

    private String commentOwner;

    private String text;

    private String createdAt;

    private String updatedAt;

}
