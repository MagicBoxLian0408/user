package kr.magicbox.user.application.port.in;

import kr.magicbox.user.application.dto.command.EndSessionCommand;
import kr.magicbox.user.application.dto.command.StartSessionCommand;

public interface ManageUserSessionUseCase {
    void startSession(StartSessionCommand command);
    void endSession(EndSessionCommand command);
}
