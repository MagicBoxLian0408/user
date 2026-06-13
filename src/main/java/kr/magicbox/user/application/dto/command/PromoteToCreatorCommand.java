package kr.magicbox.user.application.dto.command;

import kr.magicbox.user.domain.vo.UserId;

public record PromoteToCreatorCommand(UserId userId) {
    public static PromoteToCreatorCommand of(UserId userId) {
        return new PromoteToCreatorCommand(userId);
    }
}
