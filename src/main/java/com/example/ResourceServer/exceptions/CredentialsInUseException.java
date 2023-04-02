package com.example.ResourceServer.exceptions;

public class CredentialsInUseException extends Exception{
    public CredentialsInUseException(String message) {
        super(message);
    }
}
