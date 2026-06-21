package kr.magicbox.user.application.port.out;

import kr.magicbox.user.application.dto.result.UserReviewResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface ReviewQueryPort {
    CompletableFuture<List<UserReviewResult>> getAllReviewsByUserId(Long userId);
}
