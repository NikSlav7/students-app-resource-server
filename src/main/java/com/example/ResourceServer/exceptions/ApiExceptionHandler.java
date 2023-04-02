package com.example.ResourceServer.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(value = {CredentialsInUseException.class, BadTokenException.class})
    public ResponseEntity<Object> handleException(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
