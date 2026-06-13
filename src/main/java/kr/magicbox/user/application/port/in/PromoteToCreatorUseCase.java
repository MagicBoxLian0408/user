package kr.magicbox.user.application.port.in;

import kr.magicbox.user.application.dto.command.PromoteToCreatorCommand;

public interface PromoteToCreatorUseCase {
    void promoteToCreator(PromoteToCreatorCommand command);
}
