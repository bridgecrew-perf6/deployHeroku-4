package com.company.controller;


import com.company.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExcceptionHandleController {

    @ExceptionHandler({EmailAlreadyException.class, AppBadRequestException.class,
            ItemNotFoundException.class, PasswordOrEmailWrongEmail.class})
    public ResponseEntity<?> handleBadRequestException(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({AppForbiddenexception.class})
    public ResponseEntity<?> handler(RuntimeException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler({TokenNotVaildException.class})
    public ResponseEntity<?> tokenNotValid(RuntimeException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }


}
