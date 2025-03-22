package com.onseju.userservice.account.exception;

import com.onseju.userservice.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InsufficientBalanceException extends BaseException {
	public InsufficientBalanceException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}

