package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Branch not found!")
public class BranchNotFoundException extends RuntimeException{
    public BranchNotFoundException(String msg) {
        super(msg);
    }
}
