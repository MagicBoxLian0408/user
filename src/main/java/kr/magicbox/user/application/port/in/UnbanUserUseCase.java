package kr.magicbox.user.application.port.in;

import kr.magicbox.user.application.dto.command.UnbanUserCommand;

public interface UnbanUserUseCase {

    void unbanUser(UnbanUserCommand command);
}
