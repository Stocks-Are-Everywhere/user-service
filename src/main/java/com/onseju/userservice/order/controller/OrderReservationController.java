package com.onseju.userservice.order.controller;

import com.onseju.userservice.order.controller.request.OrderValidationRequest;
import com.onseju.userservice.order.controller.response.OrderValidationResponse;
import com.onseju.userservice.order.service.OrderReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class OrderReservationController {

    private final OrderReservationService orderReservationService;

    @PostMapping("/order-reservation")
    public ResponseEntity<OrderValidationResponse> validateAccount(@RequestBody OrderValidationRequest request) {
        return ResponseEntity.ok(orderReservationService.reserve(request));
    }
}
