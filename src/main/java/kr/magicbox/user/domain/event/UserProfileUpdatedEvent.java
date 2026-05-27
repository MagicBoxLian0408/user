package kr.magicbox.user.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.magicbox.user.domain.vo.UserId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UserProfileUpdatedEvent(
        @JsonProperty("user_id") UserId userId,
        @JsonProperty("before") ProfileSnapshot before,
        @JsonProperty("after") ProfileSnapshot after,
        @JsonProperty("occurred_at") Instant occurredAt
) implements UserDomainEvent {

    public record ProfileSnapshot(
            @JsonProperty("nickname") String nickname,
            @JsonProperty("profile_image_url") String profileImageUrl
    ) {}

    @Override
    public String key() {
        return userId.value().toString();
    }

    @Override
    public UserDomainEventType eventType() {
        return UserDomainEventType.USER_PROFILE_UPDATED;
    }
}
