package kr.magicbox.user.application.dto.command;

import kr.magicbox.user.domain.vo.UserId;
import lombok.Builder;

@Builder
public record BanUserCommand(
        UserId userId
) {
    public static BanUserCommand of(UserId userId) {
        return new BanUserCommand(userId);
    }
}
