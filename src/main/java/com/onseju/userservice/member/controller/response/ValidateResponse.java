package com.onseju.userservice.member.controller.response;

import com.onseju.userservice.account.domain.Type;

public record ValidateResponse(boolean valid, String message, Long accountId, Type type) {
}
