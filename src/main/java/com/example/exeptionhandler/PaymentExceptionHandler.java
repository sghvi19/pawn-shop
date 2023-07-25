package com.example.exeptionhandler;

import com.example.Controller.PaymentController;
import com.example.exception.ItemNotFoundException;
import com.example.exception.PaymentNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = PaymentController.class)
public class PaymentExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<Object> handleNoSuchElementException(PaymentNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Object> handleNoSuchElementException(ItemNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
