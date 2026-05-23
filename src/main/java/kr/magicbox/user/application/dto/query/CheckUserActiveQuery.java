package kr.magicbox.user.application.dto.query;

import kr.magicbox.user.domain.vo.UserId;
import lombok.Builder;

@Builder
public record CheckUserActiveQuery(
        UserId userId
) {
    public static CheckUserActiveQuery of(UserId userId) {
        return new CheckUserActiveQuery(userId);
    }
}
