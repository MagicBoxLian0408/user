package kr.magicbox.user.application.service;

import kr.magicbox.user.application.dto.command.WithdrawUserCommand;
import kr.magicbox.user.application.port.in.WithdrawUserUseCase;
import kr.magicbox.user.application.port.out.UserOutboxPort;
import kr.magicbox.user.application.port.out.UserRepositoryPort;
import kr.magicbox.user.domain.aggregate.User;
import kr.magicbox.user.domain.event.UserWithdrawnEvent;
import kr.magicbox.user.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class WithdrawUserService implements WithdrawUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserOutboxPort eventRepositoryPort;

    @Override
    @Transactional
    public void withdrawUser(WithdrawUserCommand command) {
        User user = userRepositoryPort.getUserById(command.userId())
                .orElseThrow(UserNotFoundException::new);

        user.delete();
        userRepositoryPort.update(user);

        eventRepositoryPort.save(
                UserWithdrawnEvent.builder()
                        .userId(command.userId())
                        .occurredAt(Instant.now())
                        .build()
        );
    }
}
