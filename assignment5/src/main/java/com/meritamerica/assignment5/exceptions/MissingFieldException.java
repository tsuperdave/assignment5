package com.meritamerica.assignment5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MissingFieldException extends RuntimeException {
		
	public MissingFieldException(String message) {
		super(message);
	}
	
	@Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
