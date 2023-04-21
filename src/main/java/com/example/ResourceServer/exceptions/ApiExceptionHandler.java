package com.example.ResourceServer.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(value = {InternalError.class, CredentialsInUseException.class, BadTokenException.class, WrongFileException.class,
            UserNotFoundException.class, WrongDataSentException.class, AuthServerError.class,
            NoSuchEntityException.class})
    public ResponseEntity<Object> handleException(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
