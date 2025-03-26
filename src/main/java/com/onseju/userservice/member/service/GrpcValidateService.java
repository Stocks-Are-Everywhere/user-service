package com.onseju.userservice.member.service;
import com.onseju.orderservice.grpc.GrpcValidateRequest;
import com.onseju.orderservice.grpc.GrpcValidateResponse;
import com.onseju.orderservice.grpc.OrderType;
import com.onseju.orderservice.grpc.OrderValidationServiceGrpc;
import com.onseju.userservice.account.domain.Account;
import com.onseju.userservice.account.domain.Type;
import com.onseju.userservice.account.service.AccountRepository;
import com.onseju.userservice.account.service.dto.CreateOrderParams;
import com.onseju.userservice.holding.domain.Holdings;
import com.onseju.userservice.holding.service.HoldingsRepository;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;




import java.math.BigDecimal;
import java.time.LocalDateTime;

import net.devh.boot.grpc.server.service.GrpcService;

@AllArgsConstructor
@GrpcService
public class GrpcValidateService extends OrderValidationServiceGrpc.OrderValidationServiceImplBase {

	private final HoldingsRepository holdingsRepository;
	private final AccountRepository accountRepository;

	@Override
	public void validateOrder(GrpcValidateRequest request, StreamObserver<GrpcValidateResponse> responseObserver) {
		try {
			// gRPC 요청을 내부 도메인 객체로 변환
			Type type = convertToJavaType(request.getType());
			BigDecimal totalQuantity = new BigDecimal(request.getTotalQuantity());
			BigDecimal price = new BigDecimal(request.getPrice());
			LocalDateTime now = LocalDateTime.parse(request.getNow());
			Long memberId = request.getMemberId();
			String companyCode = request.getCompanyCode();

			// 계정 정보 조회
			Account account = accountRepository.getByMemberId(memberId);

			// 주문 파라미터 생성
			CreateOrderParams params = new CreateOrderParams(
				companyCode, type, totalQuantity, price, now, memberId
			);

			// 유효성 검증
			boolean isValid = false;
			if (type == Type.LIMIT_BUY || type == Type.MARKET_BUY) {
				isValid = validateAccount(params, account);
			} else {
				isValid = validateHoldings(account.getId(), params);
			}

			// gRPC 응답 생성
			GrpcValidateResponse response = GrpcValidateResponse.newBuilder()
				.setIsValid(isValid)
				.setMessage(isValid ? "주문이 유효합니다" : "주문이 유효하지 않습니다")
				.setAccountId(account.getId())
				.setType(request.getType())
				.build();

			// 응답 전송
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		} catch (Exception e) {
			// 오류 처리
			GrpcValidateResponse errorResponse = GrpcValidateResponse.newBuilder()
				.setIsValid(false)
				.setMessage("오류 발생: " + e.getMessage())
				.build();

			responseObserver.onNext(errorResponse);
			responseObserver.onCompleted();
		}
	}

	private Type convertToJavaType(OrderType grpcType) {
		return switch (grpcType) {
			case LIMIT_BUY -> Type.LIMIT_BUY;
			case LIMIT_SELL -> Type.LIMIT_SELL;
			case MARKET_BUY -> Type.MARKET_BUY;
			case MARKET_SELL -> Type.MARKET_SELL;
			default -> throw new IllegalArgumentException("지원하지 않는 주문 유형: " + grpcType);
		};
	}

	private boolean validateAccount(final CreateOrderParams params, final Account account) {
		return account.validateDepositBalance(params.price().multiply(params.totalQuantity()));
	}

	private boolean validateHoldings(final Long accountId, final CreateOrderParams params) {
		final Holdings holdings = holdingsRepository.getByAccountIdAndCompanyCode(accountId, params.companyCode());
		boolean valid_one = holdings.validateExistHoldings();
		boolean valid_two = holdings.validateEnoughHoldings(params.totalQuantity());
		return valid_one && valid_two;
	}
}
