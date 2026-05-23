package kr.magicbox.user.adapter.in.kafka;

import kr.magicbox.user.adapter.in.kafka.annotation.Idempotent;
import kr.magicbox.user.adapter.in.kafka.event.LoginEvent;
import kr.magicbox.user.adapter.in.kafka.event.LogoutEvent;
import kr.magicbox.user.adapter.out.persistence.repository.UserInboxRepository;
import kr.magicbox.user.application.dto.command.EndSessionCommand;
import kr.magicbox.user.application.dto.command.StartSessionCommand;
import kr.magicbox.user.application.port.in.ManageUserSessionUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Component;
import kr.magicbox.user.global.exception.BusinessException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthEventKafkaListener {

    private final ManageUserSessionUseCase manageUserSessionUseCase;
    private final UserInboxRepository userInboxRepository;

    @Idempotent
    @RetryableTopic(dltStrategy = DltStrategy.FAIL_ON_ERROR, dltTopicSuffix = "-dlt", exclude = {kr.magicbox.user.global.exception.BusinessException.class})
    @KafkaListener(topics = "outbox.event.user-logged-in", groupId = "user-service")
    public void handleLoginEvent(ConsumerRecord<String, LoginEvent> record) {
        LoginEvent event = record.value();
        manageUserSessionUseCase.startSession(StartSessionCommand.of(event.userId(), event.occurredAt()));
    }

    @Idempotent
    @RetryableTopic(dltStrategy = DltStrategy.FAIL_ON_ERROR, dltTopicSuffix = "-dlt", exclude = {kr.magicbox.user.global.exception.BusinessException.class})
    @KafkaListener(topics = "outbox.event.user-logged-out", groupId = "user-service")
    public void handleLogoutEvent(ConsumerRecord<String, LogoutEvent> record) {
        LogoutEvent event = record.value();
        manageUserSessionUseCase.endSession(EndSessionCommand.of(event.userId(), event.occurredAt()));
    }

    @DltHandler
    public void handleDlt(ConsumerRecord<String, ?> consumerRecord) {
        log.error("[Inbox] DLT 전환. topic={}, partition={}, offset={}", consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset());
        userInboxRepository.findByTopicAndPartitionAndOffset(consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset())
                .ifPresent(inbox -> inbox.markDeadLettered());
    }
}
