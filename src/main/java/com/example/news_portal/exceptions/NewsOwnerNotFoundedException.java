package com.example.news_portal.exceptions;

public class NewsOwnerNotFoundedException extends RuntimeException {
    public NewsOwnerNotFoundedException(String message) {
        super(message);
    }
}
