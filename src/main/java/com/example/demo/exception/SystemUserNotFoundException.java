package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SystemUserNotFoundException extends RuntimeException {
    public SystemUserNotFoundException(String s) {
        super(s);
    }

    public SystemUserNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
