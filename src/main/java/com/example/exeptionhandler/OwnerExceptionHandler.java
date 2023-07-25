package com.example.exeptionhandler;

import com.example.Controller.OwnerController;
import com.example.exception.OwnerAlreadyExistsException;
import com.example.exception.OwnerNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = OwnerController.class)
public class OwnerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<Object> handleNoSuchElementException(OwnerNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(OwnerAlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsElementException(OwnerAlreadyExistsException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
