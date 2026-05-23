package kr.magicbox.user.application.dto.command;

import kr.magicbox.user.domain.vo.Nickname;
import kr.magicbox.user.domain.vo.UserId;
import lombok.Builder;

@Builder
public record UpdateUserProfileCommand(
        UserId userId,
        Nickname nickname,
        String profile,
        Boolean isReviewVisible
) {
    public static UpdateUserProfileCommand of(UserId userId, Nickname nickname, String profile, Boolean isReviewVisible) {
        return new UpdateUserProfileCommand(userId, nickname, profile, isReviewVisible);
    }
}
