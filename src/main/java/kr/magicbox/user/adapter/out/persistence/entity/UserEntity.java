package kr.magicbox.user.adapter.out.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import kr.magicbox.user.domain.aggregate.User;
import kr.magicbox.user.domain.constants.UserPolicyConstants;
import kr.magicbox.user.domain.enums.OAuth2Provider;
import kr.magicbox.user.domain.enums.UserRole;
import kr.magicbox.user.domain.enums.UserStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserEntity extends BaseEntity {

    @Size(min = UserPolicyConstants.nicknameMinLength, max = UserPolicyConstants.nicknameMaxLength,
          message = "닉네임은 {min}자 이상 {max}자 이내여야 합니다")
    @Column(unique = true, nullable = false, length = UserPolicyConstants.nicknameMaxLength)
    private String nickname;

    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    @Column(nullable = false)
    private String email;

    @NotNull(message = "사용자 상태는 필수입니다")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @NotNull(message = "사용자 역할은 필수입니다")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    private Instant lastLoginAt;

    @NotNull(message = "활성 상태는 필수입니다")
    @Column(nullable = false)
    private Boolean isActive;

    @NotBlank(message = "프로필은 필수입니다")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String profile;

    @NotNull(message = "총 사용시간은 필수입니다")
    @Column(nullable = false)
    private Duration totalUsageTime;

    @NotNull(message = "리뷰 표시 여부는 필수입니다")
    @Column(nullable = false)
    private Boolean isReviewVisible;

    @NotBlank(message = "OAuth2 ID는 필수입니다")
    @Column(nullable = false, unique = true, updatable = false)
    private String oauth2Id;

    @NotNull(message = "OAuth2 제공자는 필수입니다")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private OAuth2Provider oauth2Provider;

    @Version
    private Long version;

    @Builder
    public UserEntity(String nickname, String email, UserStatus status, UserRole role, String profile, String oauth2Id, OAuth2Provider oauth2Provider) {
        this.nickname = nickname;
        this.email = email;
        this.status = status;
        this.role = role;
        this.profile = profile;
        this.oauth2Id = oauth2Id;
        this.oauth2Provider = oauth2Provider;
        this.isActive = false;
        this.lastLoginAt = Instant.now();
        this.totalUsageTime = Duration.ZERO;
        this.isReviewVisible = true;
    }

    public void updateFromDomain(User user) {
        this.nickname = user.getNickname();
        this.status = user.getStatus();
        this.role = user.getRole();
        this.profile = user.getProfile();
        this.isActive = user.isActive();
        this.isReviewVisible = user.canShowReview();
        this.lastLoginAt = user.getLastLoginAt();
        this.totalUsageTime = user.getTotalUsageTime();
    }
}
