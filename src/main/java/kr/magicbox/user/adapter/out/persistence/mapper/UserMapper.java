package kr.magicbox.user.adapter.out.persistence.mapper;

import kr.magicbox.user.domain.aggregate.User;
import kr.magicbox.user.domain.vo.UserId;
import kr.magicbox.user.domain.vo.Nickname;
import kr.magicbox.user.adapter.out.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .status(user.getStatus())
                .role(user.getRole())
                .profile(user.getProfile())
                .oauth2Id(user.getOauth2Id())
                .oauth2Provider(user.getOauth2Provider())
                .build();
    }

    public User toDomain(UserEntity entity) {
        return User.reconstructBuilder()
                .id(UserId.of(entity.getId()))
                .nickname(entity.getNickname() != null ? Nickname.of(entity.getNickname()) : null)
                .email(entity.getEmail())
                .status(entity.getStatus())
                .role(entity.getRole())
                .profile(entity.getProfile())
                .oauth2Id(entity.getOauth2Id())
                .oauth2Provider(entity.getOauth2Provider())
                .isReviewVisible(entity.getIsReviewVisible())
                .isActive(entity.getIsActive())
                .lastLoginAt(entity.getLastLoginAt())
                .totalUsageTime(entity.getTotalUsageTime())
                .build();
    }

    public void updateEntity(User user, UserEntity entity) {
        // 기존 Entity를 Domain 정보로 업데이트
        entity.updateFromDomain(user);
    }
}
