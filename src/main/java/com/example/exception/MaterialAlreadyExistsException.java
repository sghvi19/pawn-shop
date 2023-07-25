package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Material already exists!")
public class MaterialAlreadyExistsException extends RuntimeException{
    public MaterialAlreadyExistsException(String msg) {
        super(msg);
    }
}
