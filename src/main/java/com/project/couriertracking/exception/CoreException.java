package com.project.couriertracking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CoreException extends RuntimeException {

    private final String key;
    private final String[] args;
    private final HttpStatus httpStatus;

    public CoreException(String key, HttpStatus httpStatus) {
        this.key = key;
        this.httpStatus = httpStatus;
        this.args = new String[0];
    }
}
