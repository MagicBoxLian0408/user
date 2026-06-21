package kr.magicbox.user.adapter.out.communication.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.grpc.ManagedChannel;
import kr.magicbox.user.adapter.out.communication.ServiceHost;
import kr.magicbox.user.adapter.out.communication.grpc.exception.ReviewServiceUnavailableException;
import kr.magicbox.user.application.dto.result.UserReviewResult;
import kr.magicbox.user.application.port.out.ReviewQueryPort;
import kr.magicbox.user.grpc.review.GetAllReviewsByUserIdRequest;
import kr.magicbox.user.grpc.review.GetAllReviewsByUserIdResponse;
import kr.magicbox.user.grpc.review.Review;
import kr.magicbox.user.grpc.review.ReviewServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewQueryGrpcAdapter implements ReviewQueryPort {
    private final GrpcChannelFactory grpcChannelFactory;

    @Override
    @CircuitBreaker(name = "reviewService", fallbackMethod = "getAllReviewsFallback")
    @TimeLimiter(name = "reviewService", fallbackMethod = "getAllReviewsFallback")
    public CompletableFuture<List<UserReviewResult>> getAllReviewsByUserId(Long userId) {
        GetAllReviewsByUserIdRequest request = GetAllReviewsByUserIdRequest.newBuilder()
            .setUserId(userId)
            .build();

        ManagedChannel channel = grpcChannelFactory.createChannel(ServiceHost.REVIEW.getHostName());
        ReviewServiceGrpc.ReviewServiceFutureStub reviewStub = ReviewServiceGrpc.newFutureStub(channel);
        ListenableFuture<GetAllReviewsByUserIdResponse> future = reviewStub.getAllReviewsByUserId(request);

        CompletableFuture<List<UserReviewResult>> result = new CompletableFuture<>();
        future.addListener(() -> {
            try {
                result.complete(future.get().getReviewsList().stream()
                    .map(this::convertToUserReviewDto)
                    .toList());
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        }, Runnable::run);
        return result;
    }

    @SuppressWarnings("unused")
    private CompletableFuture<List<UserReviewResult>> getAllReviewsFallback(Long userId, Throwable throwable) {
        log.warn("리뷰 서비스 연결 실패");
        throw new ReviewServiceUnavailableException(throwable);
    }

    private UserReviewResult convertToUserReviewDto(Review grpcReview) {
        return UserReviewResult.builder()
            .reviewId(grpcReview.getReviewId())
            .content(grpcReview.getContent())
            .createdAt(Instant.ofEpochSecond(grpcReview.getCreatedAt().getSeconds()))
            .build();
    }
}