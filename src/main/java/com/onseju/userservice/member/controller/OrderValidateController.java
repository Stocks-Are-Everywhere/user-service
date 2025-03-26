package com.onseju.userservice.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onseju.userservice.account.service.AccountService;
import com.onseju.userservice.member.controller.request.ValidateRequest;
import com.onseju.userservice.member.controller.response.ValidateResponse;
import com.onseju.userservice.member.service.ValidateService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class OrderValidateController {

	private final ValidateService validateService;

	@PostMapping("/validation")
	public ResponseEntity<ValidateResponse> validate(@RequestBody ValidateRequest request) {
		ValidateResponse response = validateService.validate(request);
		return ResponseEntity.ok(response);
	}

}
