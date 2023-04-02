package com.example.ResourceServer.exceptions;

public class BadTokenException extends Exception{
    public BadTokenException(String message) {
        super(message);
    }
}
