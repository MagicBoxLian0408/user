package kr.magicbox.user.adapter.in.web.dto.response;

import kr.magicbox.user.domain.enums.UserRole;
import lombok.Builder;

@Builder
public record GetMyUserProfileResponse(
        Long id,
        String nickname,
        String profile,
        UserRole role
) {
}
