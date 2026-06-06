package kr.magicbox.user.adapter.in.kafka;

import kr.magicbox.user.application.dto.command.EndSessionCommand;
import kr.magicbox.user.application.port.in.ManageUserSessionUseCase;
import kr.magicbox.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import kr.magicbox.user.global.exception.BusinessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class SseDisconnectedKafkaListener {

    private final ManageUserSessionUseCase manageUserSessionUseCase;

    @RetryableTopic(dltStrategy = DltStrategy.FAIL_ON_ERROR, dltTopicSuffix = "-dlt", exclude = {BusinessException.class})
    @KafkaListener(topics = "sse.disconnected", groupId = "user-service", containerFactory = "stringKafkaListenerContainerFactory")
    public void handleDisconnected(ConsumerRecord<String, String> record) {
        Long userId = Long.parseLong(record.key());
        log.debug("sse.disconnected 수신 userId={}", userId);
        manageUserSessionUseCase.endSession(EndSessionCommand.of(UserId.of(userId), Instant.now()));
    }
}
