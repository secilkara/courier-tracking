package com.project.couriertracking.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CoreException {

    public NotFoundException(String key) {
        super(key, HttpStatus.NOT_FOUND);
    }
}
