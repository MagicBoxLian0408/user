package kr.magicbox.user.application.port.out;

import kr.magicbox.user.domain.aggregate.User;
import kr.magicbox.user.domain.enums.OAuth2Provider;
import kr.magicbox.user.domain.vo.Nickname;
import kr.magicbox.user.domain.vo.UserId;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> getUserByNickname(Nickname nickname);
    Optional<User> getUserById(UserId userId);
    Optional<User> findByOauth2IdAndProvider(String oauth2Id, OAuth2Provider provider);
    User save(User user);
    void update(User user);
    Map<Long, String> getNicknamesByIds(List<Long> userIds);
}