package kr.magicbox.user.adapter.out.persistence.repository;

import kr.magicbox.user.adapter.out.persistence.entity.UserEntity;
import kr.magicbox.user.domain.enums.OAuth2Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u where u.nickname = :nickname")
    Optional<UserEntity> findByNickname(String nickname);

    @Query("select u from UserEntity u where u.oauth2Id = :oauth2Id and u.oauth2Provider = :provider")
    Optional<UserEntity> findByOauth2IdAndOauth2Provider(String oauth2Id, OAuth2Provider provider);

    @Query("select u.id, u.nickname from UserEntity u where u.id in :ids")
    List<Object[]> findNicknamesByIds(List<Long> ids);
}
