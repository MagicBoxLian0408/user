package kr.magicbox.user.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.magicbox.user.domain.vo.UserId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UserUnbannedEvent(
        @JsonProperty("user_id") UserId userId,
        @JsonProperty("occurred_at") Instant occurredAt
) implements UserDomainEvent {

    @Override
    public String key() {
        return userId.value().toString();
    }

    @Override
    public UserDomainEventType eventType() {
        return UserDomainEventType.USER_UNBANNED;
    }
}
