package kr.magicbox.user.application.port.in;

import kr.magicbox.user.application.dto.command.BanUserCommand;

public interface BanUserUseCase {

    void banUser(BanUserCommand command);
}
