package com.example.wefly_app.util.exception;

public class FileHandlingException extends RuntimeException{
    public FileHandlingException(String message){
        super(message);
    }

    public FileHandlingException(String message, Throwable cause){
        super(message, cause);
    }
}
