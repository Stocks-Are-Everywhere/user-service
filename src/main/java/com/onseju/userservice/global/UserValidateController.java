package com.onseju.userservice.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user-service")
@RequiredArgsConstructor
public class UserValidateController {

	private final UserValidateService userValidateService;

	@PostMapping("/validate")
	public ResponseEntity<Void> validateUserInfoForOrder(@RequestBody final BeforeTradeOrderDto dto) {
		userValidateService.validateUserInfoForOrder(dto);
		return ResponseEntity.ok().build();
	}

}
