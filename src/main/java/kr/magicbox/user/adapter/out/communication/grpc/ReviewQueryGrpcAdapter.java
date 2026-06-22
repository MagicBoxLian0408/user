package kr.magicbox.user.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.grpc.ManagedChannel;
import kr.magicbox.user.adapter.out.communication.grpc.exception.ReviewServiceUnavailableException;
import kr.magicbox.user.application.dto.result.UserReviewResult;
import kr.magicbox.user.application.port.out.ReviewQueryPort;
import kr.magicbox.user.grpc.review.GetAllReviewsByUserIdRequest;
import kr.magicbox.user.grpc.review.Review;
import kr.magicbox.user.grpc.review.ReviewServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewQueryGrpcAdapter implements ReviewQueryPort {

    private final ManagedChannel reviewManagedChannel;

    @Override
    @CircuitBreaker(name = "reviewService", fallbackMethod = "getAllReviewsFallback")
    @TimeLimiter(name = "reviewService", fallbackMethod = "getAllReviewsFallback")
    public CompletableFuture<List<UserReviewResult>> getAllReviewsByUserId(Long userId) {
        return GrpcFutures.toCompletable(
                ReviewServiceGrpc.newFutureStub(reviewManagedChannel).getAllReviewsByUserId(
                        GetAllReviewsByUserIdRequest.newBuilder().setUserId(userId).build()
                )
        ).thenApply(response -> response.getReviewsList().stream()
                .map(this::convertToUserReviewDto)
                .toList());
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
