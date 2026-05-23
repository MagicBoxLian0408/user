package kr.magicbox.user.application.service;

import kr.magicbox.user.application.dto.query.CheckUserActiveQuery;
import kr.magicbox.user.application.port.in.CheckUserActiveUseCase;
import kr.magicbox.user.application.port.out.UserRepositoryPort;
import kr.magicbox.user.domain.aggregate.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckUserActiveService implements CheckUserActiveUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public boolean isActive(CheckUserActiveQuery query) {
        return userRepositoryPort.getUserById(query.userId())
                .map(User::isActive)
                .orElse(false);
    }
}
