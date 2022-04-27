package com.company.exception;

public class ArticleStatusNotExistsException extends RuntimeException{
    public ArticleStatusNotExistsException(String message) {
        super(message);
    }
}
