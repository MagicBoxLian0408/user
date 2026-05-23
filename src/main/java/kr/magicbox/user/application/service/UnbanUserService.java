package kr.magicbox.user.application.service;

import kr.magicbox.user.application.dto.command.UnbanUserCommand;
import kr.magicbox.user.application.port.in.UnbanUserUseCase;
import kr.magicbox.user.application.port.out.UserOutboxPort;
import kr.magicbox.user.application.port.out.UserRepositoryPort;
import kr.magicbox.user.domain.aggregate.User;
import kr.magicbox.user.domain.event.UserUnbannedEvent;
import kr.magicbox.user.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UnbanUserService implements UnbanUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserOutboxPort userOutboxPort;

    @Override
    @Transactional
    public void unbanUser(UnbanUserCommand command) {
        User user = userRepositoryPort.getUserById(command.userId())
                .orElseThrow(UserNotFoundException::new);

        user.unban();
        userRepositoryPort.update(user);

        UserUnbannedEvent event = UserUnbannedEvent.builder()
                .userId(user.getId())
                .occurredAt(Instant.now())
                .build();
        userOutboxPort.save(event);
    }
}
