package kr.magicbox.user.adapter.out.persistence.repository;

import kr.magicbox.user.adapter.out.persistence.entity.UserInboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInboxRepository extends JpaRepository<UserInboxEntity, Long> {

    boolean existsByKey(String key);

    Optional<UserInboxEntity> findByTopicAndPartitionAndOffset(String topic, Integer partition, Long offset);
}