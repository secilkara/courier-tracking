package com.project.couriertracking.controller.advice;

import com.project.couriertracking.exception.CoreException;
import com.project.couriertracking.model.response.ErrorResponse;
import com.project.couriertracking.util.ClockUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    private final MessageSource messageSource;
    private static final Locale EN = new Locale("en");

    @Autowired
    private ExceptionHandler(MessageSource messageSource) {
            this.messageSource = messageSource;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("Exception occurred.", exception);
        ErrorResponse errorResponse = new ErrorResponse(
                "Exception",
                ClockUtils.now(),
                "Exception occurred.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CoreException.class)
    public ResponseEntity<ErrorResponse> handleCoreException(CoreException exception) {
        log.error("CoreException occurred.", exception);
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(ErrorResponse.builder()
                              .exception(exception.getClass().getName())
                              .error(messageSource.getMessage(exception.getKey(), exception.getArgs(), EN))
                              .build());
    }
}
