package kr.magicbox.user.adapter.in.web.dto.response;

import kr.magicbox.user.application.dto.result.UserReviewResult;
import kr.magicbox.user.domain.enums.UserRole;
import lombok.Builder;

import java.util.List;

@Builder
public record GetUserProfileResponse(
        String nickname,
        String profile,
        List<UserReviewResult> reviews,
        UserRole role,
        boolean isMe
) {
}
