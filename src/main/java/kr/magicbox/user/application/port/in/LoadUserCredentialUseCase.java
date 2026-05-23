package kr.magicbox.user.application.port.in;

import kr.magicbox.user.application.dto.command.LoadUserCredentialCommand;
import kr.magicbox.user.application.dto.result.LoadUserCredentialResult;

public interface LoadUserCredentialUseCase {
    LoadUserCredentialResult loadUserCredential(LoadUserCredentialCommand command);
}
