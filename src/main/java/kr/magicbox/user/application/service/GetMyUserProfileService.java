package kr.magicbox.user.application.service;

import kr.magicbox.user.application.dto.query.GetMyUserProfileQuery;
import kr.magicbox.user.application.dto.result.GetMyUserProfileResult;
import kr.magicbox.user.application.port.in.GetMyUserProfileUseCase;
import kr.magicbox.user.application.port.out.UserRepositoryPort;
import kr.magicbox.user.domain.aggregate.User;
import kr.magicbox.user.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetMyUserProfileService implements GetMyUserProfileUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GetMyUserProfileResult getMyUserProfile(GetMyUserProfileQuery query) {
        User user = userRepositoryPort.getUserById(query.userId())
                .orElseThrow(UserNotFoundException::new);

        return GetMyUserProfileResult.builder()
                .id(user.getId().value())
                .nickname(user.getNickname())
                .profile(user.getProfile())
                .role(user.getRole())
                .build();
    }
}
