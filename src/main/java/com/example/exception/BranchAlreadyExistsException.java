package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Branch already exists!")
public class BranchAlreadyExistsException extends RuntimeException {

    public BranchAlreadyExistsException(String msg) {
        super(msg);
    }
}
