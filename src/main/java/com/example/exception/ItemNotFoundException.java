package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Item already exists!")
public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(String msg) {
        super(msg);
    }

}
