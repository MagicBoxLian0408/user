package kr.magicbox.user.domain.aggregate;

import kr.magicbox.user.domain.exception.*;
import kr.magicbox.user.domain.vo.Nickname;
import kr.magicbox.user.domain.vo.UserId;
import kr.magicbox.user.domain.enums.UserRole;
import kr.magicbox.user.domain.enums.UserStatus;
import kr.magicbox.user.domain.enums.OAuth2Provider;
import kr.magicbox.user.domain.exception.InvalidFieldException;
import kr.magicbox.user.domain.exception.UserAlreadyBannedException;
import kr.magicbox.user.domain.exception.UserNotActiveForDeletionException;
import kr.magicbox.user.domain.exception.UserNotBannedException;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;

@Getter
public class User {
    private final UserId id;
    private Nickname nickname;
    private final String email;
    private UserStatus status;
    private UserRole role;
    private Instant lastLoginAt;
    private String profile;
    private Duration totalUsageTime;
    private Boolean isReviewVisible;
    private Boolean isActive;
    private final String oauth2Id;
    private final OAuth2Provider oauth2Provider;

    @Builder(builderMethodName = "createBuilder", builderClassName = "CreateBuilder")
    public User(Nickname nickname, String email, UserStatus status,
                UserRole role, String profile, String oauth2Id,
                OAuth2Provider oauth2Provider) {
        validateFields(email, status, oauth2Id, oauth2Provider);

        this.id = null;
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

    @Builder(builderMethodName = "reconstructBuilder", builderClassName = "ReconstructBuilder")
    public User(UserId id, Nickname nickname, String email, UserStatus status,
                UserRole role, String profile, String oauth2Id,
                OAuth2Provider oauth2Provider, Boolean isReviewVisible, Boolean isActive,
                Instant lastLoginAt, Duration totalUsageTime) {
        validateFields(email, status, oauth2Id, oauth2Provider);

        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.status = status;
        this.role = role;
        this.profile = profile;
        this.oauth2Id = oauth2Id;
        this.oauth2Provider = oauth2Provider;
        this.isActive = isActive != null ? isActive : false;
        this.lastLoginAt = lastLoginAt != null ? lastLoginAt : Instant.now();
        this.totalUsageTime = totalUsageTime != null ? totalUsageTime : Duration.ZERO;
        this.isReviewVisible = isReviewVisible != null ? isReviewVisible : true;
    }

    public String getNickname() {
        return this.nickname != null ? this.nickname.value() : null;
    }

    private void validateFields(String email, UserStatus status,
                                String oauth2Id, OAuth2Provider oauth2Provider) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidFieldException("이메일은 필수 값입니다.");
        }
        if (status == null) {
            throw new InvalidFieldException("상태는 필수 값입니다.");
        }
        if (oauth2Id == null || oauth2Id.trim().isEmpty()) {
            throw new InvalidFieldException("OAuth2 ID는 필수 값입니다.");
        }
        if (oauth2Provider == null) {
            throw new InvalidFieldException("OAuth2 제공자는 필수 값입니다.");
        }
    }

    public void updateProfile(Nickname nickname, String profile, Boolean isReviewVisible) {
        if (nickname != null) {
            this.nickname = nickname;
        }
        if (profile != null && !profile.trim().isEmpty()) {
            this.profile = profile;
        }
        if (isReviewVisible != null) {
            this.isReviewVisible = isReviewVisible;
        }
    }

    public void startSession(Instant loginAt) {
        if (UserStatus.BANNED.equals(this.status)) throw new UserBannedException();
        if (UserStatus.DELETED.equals(this.status)) throw new UserDeletedException();
        if (this.isActive()) throw new UserAlreadyActiveException();
        this.isActive = true;
        this.lastLoginAt = loginAt;
    }

    public void endSession(Instant logoutAt) {
        if (!this.isActive()) throw new UserAlreadyNotActiveException();
        if (Boolean.TRUE.equals(this.isActive) && this.lastLoginAt != null) {
            Duration sessionTime = Duration.between(this.lastLoginAt, logoutAt);
            this.totalUsageTime = this.totalUsageTime.plus(sessionTime);
        }
        this.isActive = false;
    }

    public void enableReviewVisibility() {
        this.isReviewVisible = true;
    }

    public void disableReviewVisibility() {
        this.isReviewVisible = false;
    }

    public boolean canShowReview() {
        return Boolean.TRUE.equals(this.isReviewVisible);
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(this.isActive);
    }

    public void activate() {
        this.isActive = true;
        this.status = UserStatus.ACTIVE;
    }

    public void ban() {
        if (UserStatus.BANNED.equals(this.status)) throw new UserAlreadyBannedException();
        this.status = UserStatus.BANNED;
        this.isActive = false;
    }

    public void unban() {
        if (this.status != UserStatus.BANNED) throw new UserNotBannedException();
        this.status = UserStatus.ACTIVE;
        this.isActive = false;
    }

    public void delete() {
        if (!UserStatus.ACTIVE.equals(this.status)) throw new UserNotActiveForDeletionException();
        this.isActive = false;
        this.status = UserStatus.DELETED;
    }

    public void promoteToCreator() {
        this.role = UserRole.CREATOR;
    }
}
