package com.example.ResourceServer.exceptions;

public class NoSuchEntityException extends Exception{
    public NoSuchEntityException(String message) {
        super(message);
    }
}
