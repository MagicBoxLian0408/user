package kr.magicbox.user.adapter.in.kafka;

import kr.magicbox.user.application.dto.command.StartSessionCommand;
import kr.magicbox.user.application.port.in.ManageUserSessionUseCase;
import kr.magicbox.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Component;

import java.time.Instant;
import kr.magicbox.user.global.exception.BusinessException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SseConnectedKafkaListener {

    private final ManageUserSessionUseCase manageUserSessionUseCase;

    @RetryableTopic(dltStrategy = DltStrategy.FAIL_ON_ERROR, dltTopicSuffix = "-dlt", exclude = {kr.magicbox.user.global.exception.BusinessException.class})
    @KafkaListener(topics = "sse.connected", groupId = "user-service", containerFactory = "stringKafkaListenerContainerFactory")
    public void handleConnected(ConsumerRecord<String, String> record) {
        Long userId = Long.parseLong(record.key());
        log.debug("sse.connected 수신 userId={}", userId);
        manageUserSessionUseCase.startSession(StartSessionCommand.of(UserId.of(userId), Instant.now()));
    }
}
