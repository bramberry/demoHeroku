package com.example.demo.service.exception;

public class SystemUserServiceException extends RuntimeException {
    public SystemUserServiceException(String s) {
        super(s);
    }

    public SystemUserServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
