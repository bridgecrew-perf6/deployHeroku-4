package com.company.exception;

public class PasswordOrEmailWrongEmail extends RuntimeException {
    public PasswordOrEmailWrongEmail(String message) {
        super(message);
    }
}
