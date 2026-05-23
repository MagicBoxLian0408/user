package kr.magicbox.user.application.dto.query;

import kr.magicbox.user.domain.vo.Nickname;
import kr.magicbox.user.domain.vo.UserId;
import lombok.Builder;

@Builder
public record GetUserProfileQuery(
        Nickname nickname,
        UserId requestUserId
) {
    public static GetUserProfileQuery of(Nickname nickname, UserId requestUserId) {
        return new GetUserProfileQuery(nickname, requestUserId);
    }
}
