package kr.magicbox.user.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserDomainEventType {

    USER_WITHDRAWN("user-withdrawn"),
    USER_BANNED("user-banned"),
    USER_UNBANNED("user-unbanned"),
    USER_SIGNUP("user-signup"),
    USER_PROFILE_UPDATED("user-profile-updated");

    private final String value;
}