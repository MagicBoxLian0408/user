package kr.magicbox.user.adapter.out.persistence;

import kr.magicbox.user.adapter.out.persistence.entity.UserEntity;
import kr.magicbox.user.adapter.out.persistence.mapper.UserMapper;
import kr.magicbox.user.adapter.out.persistence.repository.UserJpaRepository;
import kr.magicbox.user.application.port.out.UserRepositoryPort;
import kr.magicbox.user.domain.aggregate.User;
import kr.magicbox.user.domain.enums.OAuth2Provider;
import kr.magicbox.user.domain.vo.Nickname;
import kr.magicbox.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserJpaAdapter implements UserRepositoryPort {
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> getUserByNickname(Nickname nickname) {
        return userJpaRepository.findByNickname(nickname.value())
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> getUserById(UserId userId) {
        return userJpaRepository.findById(userId.value())
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByOauth2IdAndProvider(String oauth2Id, OAuth2Provider provider) {
        return userJpaRepository.findByOauth2IdAndOauth2Provider(oauth2Id, provider)
                .map(userMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity saved = userJpaRepository.save(entity);
        return userMapper.toDomain(saved);
    }

    @Override
    public void update(User user) {
        userJpaRepository.findById(user.getId().value())
                .ifPresent(entity -> {
                    userMapper.updateEntity(user, entity);
                    userJpaRepository.save(entity);
                });
    }
}