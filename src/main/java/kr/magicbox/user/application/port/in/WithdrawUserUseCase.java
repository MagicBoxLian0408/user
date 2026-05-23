package kr.magicbox.user.application.port.in;

import kr.magicbox.user.application.dto.command.WithdrawUserCommand;

public interface WithdrawUserUseCase {

    void withdrawUser(WithdrawUserCommand command);
}
