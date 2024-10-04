package com.example.news_portal.model;

import com.example.news_portal.validation.NewsFilterValid;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NewsFilterValid
public class NewsFilter {

    private String categoryName;

    private String newsOwner;

}
