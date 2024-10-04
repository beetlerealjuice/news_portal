package com.example.news_portal.exceptions;

public class UserNameIsPresentException extends RuntimeException {
    public UserNameIsPresentException(String message) {
        super(message);
    }
}
