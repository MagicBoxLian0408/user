package kr.magicbox.user.application.service;

import kr.magicbox.user.application.dto.command.PromoteToCreatorCommand;
import kr.magicbox.user.application.port.in.PromoteToCreatorUseCase;
import kr.magicbox.user.application.port.out.UserRepositoryPort;
import kr.magicbox.user.domain.aggregate.User;
import kr.magicbox.user.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromoteToCreatorService implements PromoteToCreatorUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Transactional
    @Override
    public void promoteToCreator(PromoteToCreatorCommand command) {
        User user = userRepositoryPort.getUserById(command.userId())
                .orElseThrow(UserNotFoundException::new);
        user.promoteToCreator();
        userRepositoryPort.update(user);
    }
}
