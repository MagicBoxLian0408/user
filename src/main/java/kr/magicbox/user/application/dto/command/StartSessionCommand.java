package kr.magicbox.user.application.dto.command;

import kr.magicbox.user.domain.vo.UserId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record StartSessionCommand(
        UserId userId,
        Instant loginAt
) {
    public static StartSessionCommand of(UserId userId, Instant loginAt) {
        return new StartSessionCommand(userId, loginAt);
    }
}
