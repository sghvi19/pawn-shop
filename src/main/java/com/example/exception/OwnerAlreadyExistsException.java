package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Owner already exists!")
public class OwnerAlreadyExistsException extends RuntimeException{
    public OwnerAlreadyExistsException(String msg) {
        super(msg);
    }
}
