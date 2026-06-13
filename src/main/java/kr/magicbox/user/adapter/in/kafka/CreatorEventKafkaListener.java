package kr.magicbox.user.adapter.in.kafka;

import kr.magicbox.user.adapter.in.kafka.annotation.Idempotent;
import kr.magicbox.user.adapter.in.kafka.event.CreatorCertificationApprovedEvent;
import kr.magicbox.user.adapter.out.persistence.repository.UserInboxRepository;
import kr.magicbox.user.application.dto.command.PromoteToCreatorCommand;
import kr.magicbox.user.application.port.in.PromoteToCreatorUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreatorEventKafkaListener {

    private final PromoteToCreatorUseCase promoteToCreatorUseCase;
    private final UserInboxRepository userInboxRepository;

    @Idempotent
    @RetryableTopic
    @KafkaListener(topics = "outbox.event.creator-certification-approved", groupId = "user-service")
    public void handleCreatorCertificationApprovedEvent(ConsumerRecord<String, CreatorCertificationApprovedEvent> record) {
        CreatorCertificationApprovedEvent event = record.value();
        promoteToCreatorUseCase.promoteToCreator(PromoteToCreatorCommand.of(event.userId()));
    }

    @DltHandler
    public void handleDlt(ConsumerRecord<String, ?> consumerRecord) {
        log.error("[Inbox] DLT 전환. topic={}, partition={}, offset={}", consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset());
        userInboxRepository.findByTopicAndPartitionAndOffset(consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset())
                .ifPresent(inbox -> inbox.markDeadLettered());
    }
}
