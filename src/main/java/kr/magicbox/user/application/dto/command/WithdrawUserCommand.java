package kr.magicbox.user.application.dto.command;

import kr.magicbox.user.domain.vo.UserId;
import lombok.Builder;

@Builder
public record WithdrawUserCommand(
        UserId userId
) {
    public static WithdrawUserCommand of(UserId userId) {
        return new WithdrawUserCommand(userId);
    }
}
