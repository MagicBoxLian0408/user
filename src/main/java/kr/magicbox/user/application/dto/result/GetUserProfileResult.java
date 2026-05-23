package kr.magicbox.user.application.dto.result;

import kr.magicbox.user.domain.enums.UserRole;
import lombok.Builder;

import java.util.List;

@Builder
public record GetUserProfileResult(
        String nickname,
        String profile,
        List<UserReviewResult> reviews,
        UserRole role,
        boolean isMe
) {
}