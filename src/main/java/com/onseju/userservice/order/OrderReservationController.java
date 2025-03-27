package com.onseju.userservice.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user-service")
@RequiredArgsConstructor
public class OrderReservationController {

	private final OrderReservationService orderReservationService;

	@PostMapping("/validate")
	public ResponseEntity<OrderReservationResponse> validateUserInfoForOrder(@RequestBody final BeforeTradeOrderDto dto) {
		return ResponseEntity.ok(orderReservationService.validateUserInfoForOrder(dto));
	}

}
