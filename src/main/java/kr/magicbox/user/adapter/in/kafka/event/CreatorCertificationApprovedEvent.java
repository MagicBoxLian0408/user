package kr.magicbox.user.adapter.in.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.magicbox.user.domain.vo.UserId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CreatorCertificationApprovedEvent(
        @JsonProperty("user_id") UserId userId,
        @JsonProperty("occurred_at") Instant occurredAt
) implements InboxEvent {}
