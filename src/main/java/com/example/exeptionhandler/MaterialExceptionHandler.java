package com.example.exeptionhandler;

import com.example.Controller.MaterialController;
import com.example.exception.MaterialAlreadyExistsException;
import com.example.exception.MaterialNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = MaterialController.class)
public class MaterialExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MaterialNotFoundException.class)
    public ResponseEntity<Object> handleNoSuchElementException(MaterialNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(MaterialAlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsElementException(MaterialAlreadyExistsException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}
