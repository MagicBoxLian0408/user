package kr.magicbox.user.application.dto.result;

import lombok.Builder;
import java.time.Instant;

@Builder
public record UserReviewResult(
        Long reviewId,
        String content,
        Instant createdAt
) {
}