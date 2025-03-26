package com.onseju.userservice.company.exception;

import org.springframework.http.HttpStatus;

import com.onseju.orderservice.global.exception.BaseException;

public class CompanyNotFound extends BaseException {

    public CompanyNotFound() {
        super("존재하지 않는 회사 정보입니다.", HttpStatus.NOT_FOUND);
    }
}
