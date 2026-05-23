package kr.magicbox.user.application.dto.command;

import kr.magicbox.user.domain.vo.UserId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record EndSessionCommand(
        UserId userId,
        Instant logoutAt
) {
    public static EndSessionCommand of(UserId userId, Instant logoutAt) {
        return new EndSessionCommand(userId, logoutAt);
    }
}
