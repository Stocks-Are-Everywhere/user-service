package com.onseju.userservice.order;

import com.onseju.userservice.grpc.GrpcReadMemberRequest;
import com.onseju.userservice.grpc.GrpcReadMemberResponse;
import com.onseju.userservice.grpc.MemberReaderServiceGrpc;
import com.onseju.userservice.member.domain.Member;
import com.onseju.userservice.member.service.repository.MemberRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class MemberReaderService extends MemberReaderServiceGrpc.MemberReaderServiceImplBase {

    private final MemberRepository memberRepository;

    @Override
    public void readMember(
            GrpcReadMemberRequest request,
            StreamObserver<GrpcReadMemberResponse> responseObserver
    ) {
        Long accountId = request.getAccountId();
        Member member = memberRepository.getByAccountId(accountId);
        GrpcReadMemberResponse response = GrpcReadMemberResponse.newBuilder()
                .setMemberId(member.getId())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}