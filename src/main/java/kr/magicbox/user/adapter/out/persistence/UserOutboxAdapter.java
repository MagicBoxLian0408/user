package kr.magicbox.user.adapter.out.persistence;

import kr.magicbox.user.adapter.out.persistence.entity.UserOutboxEntity;
import kr.magicbox.user.adapter.out.persistence.repository.UserOutboxRepository;
import kr.magicbox.user.application.port.out.UserOutboxPort;
import kr.magicbox.user.domain.event.UserDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
@RequiredArgsConstructor
public class UserOutboxAdapter implements UserOutboxPort {

    private final UserOutboxRepository userOutboxRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void save(UserDomainEvent event) {
        String payload = objectMapper.writeValueAsString(event);
        userOutboxRepository.save(UserOutboxEntity.builder()
                .eventType(event.eventType().getValue())
                .payload(payload)
                .build());
    }
}
