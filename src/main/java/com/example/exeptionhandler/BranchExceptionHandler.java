package com.example.exeptionhandler;

import com.example.Controller.BranchController;

import com.example.exception.BranchAlreadyExistsException;
import com.example.exception.BranchNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = BranchController.class)
public class BranchExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BranchNotFoundException.class)
    public ResponseEntity<Object> handleNoSuchElementException(BranchNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BranchAlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsElementException(BranchAlreadyExistsException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}
