package kr.magicbox.user.application.dto.command;

import kr.magicbox.user.domain.vo.UserId;
import lombok.Builder;

@Builder
public record UnbanUserCommand(
        UserId userId
) {
    public static UnbanUserCommand of(UserId userId) {
        return new UnbanUserCommand(userId);
    }
}
