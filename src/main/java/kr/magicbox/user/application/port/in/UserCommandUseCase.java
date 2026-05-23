package kr.magicbox.user.application.port.in;

import kr.magicbox.user.application.dto.command.UpdateUserProfileCommand;

public interface UserCommandUseCase {
    void updateUserProfile(UpdateUserProfileCommand command);
}
