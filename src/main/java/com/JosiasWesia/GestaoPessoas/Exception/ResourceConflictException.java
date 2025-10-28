package com.JosiasWesia.GestaoPessoas.Exception;

import org.springframework.http.HttpStatus;

public class ResourceConflictException extends BusinessException  {
    public ResourceConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
