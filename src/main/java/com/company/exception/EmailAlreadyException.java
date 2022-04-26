package com.company.exception;

public class EmailAlreadyException extends RuntimeException{
    public EmailAlreadyException(String message) {
        super(message);
    }
}
