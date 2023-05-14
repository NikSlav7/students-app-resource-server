package com.example.ResourceServer.exceptions;

public class EntityExistsException extends Exception{
    public EntityExistsException(String message) {
        super(message);
    }
}
