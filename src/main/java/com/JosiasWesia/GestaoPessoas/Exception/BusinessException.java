package com.JosiasWesia.GestaoPessoas.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BusinessException extends ResponseStatusException {
    public BusinessException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
