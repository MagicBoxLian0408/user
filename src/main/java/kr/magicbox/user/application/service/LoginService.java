package kr.magicbox.user.application.service;

import kr.magicbox.user.application.dto.command.LoadUserCredentialCommand;
import kr.magicbox.user.application.dto.result.LoadUserCredentialResult;
import kr.magicbox.user.application.port.in.LoadUserCredentialUseCase;
import kr.magicbox.user.application.port.out.UserOutboxPort;
import kr.magicbox.user.domain.aggregate.User;
import kr.magicbox.user.domain.enums.UserRole;
import kr.magicbox.user.domain.enums.UserStatus;
import kr.magicbox.user.domain.exception.UserBannedException;
import kr.magicbox.user.domain.exception.UserDeletedException;

import kr.magicbox.user.application.port.out.UserRepositoryPort;
import kr.magicbox.user.domain.event.UserSignupEvent;
import kr.magicbox.user.domain.vo.Nickname;
import kr.magicbox.user.global.properties.UserProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService implements LoadUserCredentialUseCase {

    private final UserRepositoryPort userRepository;
    private final UserOutboxPort userDomainEventRepository;
    private final UserProperties userProperties;

    @Override
    @Transactional
    public LoadUserCredentialResult loadUserCredential(LoadUserCredentialCommand command) {
        return userRepository.findByOauth2IdAndProvider(command.oauth2Id(), command.provider())
                .map(this::handleExistingUser)
                .orElseGet(() -> handleNewUser(command));
    }

    private LoadUserCredentialResult handleExistingUser(User user) {
        if (UserStatus.BANNED.equals(user.getStatus())) {
            throw new UserBannedException();
        }
        if (UserStatus.DELETED.equals(user.getStatus())) {
            throw new UserDeletedException();
        }
        userRepository.update(user);
        return LoadUserCredentialResult.builder()
                .userId(user.getId())
                .userRole(user.getRole())
                .build();
    }

    private LoadUserCredentialResult handleNewUser(LoadUserCredentialCommand command) {
        String nickname = UUID.randomUUID().toString().replace("-", "").substring(0, 18);
        User user = User.createBuilder()
                .nickname(Nickname.of(nickname))
                .email(command.email())
                .role(UserRole.USER)
                .status(UserStatus.ACTIVE)
                .profile(command.profileImage() != null ? command.profileImage() : userProperties.getDefaultProfileImageUrl())
                .oauth2Id(command.oauth2Id())
                .oauth2Provider(command.provider())
                .build();
        User saved = userRepository.save(user);

        UserSignupEvent userSignupEvent = UserSignupEvent.builder()
                .eventId(saved.getId().value())
                .userId(saved.getId())
                .occurredAt(Instant.now())
                .build();

        userDomainEventRepository.save(userSignupEvent);

        return LoadUserCredentialResult.builder()
                .userId(saved.getId())
                .userRole(saved.getRole())
                .build();
    }
}