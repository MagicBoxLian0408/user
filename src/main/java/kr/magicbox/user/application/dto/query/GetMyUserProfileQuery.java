package kr.magicbox.user.application.dto.query;

import kr.magicbox.user.domain.vo.UserId;
import lombok.Builder;

@Builder
public record GetMyUserProfileQuery(
        UserId userId
) {
    public static GetMyUserProfileQuery of(UserId userId) {
        return new GetMyUserProfileQuery(userId);
    }
}
