package kr.magicbox.user.adapter.out.persistence.repository;

import kr.magicbox.user.adapter.out.persistence.entity.UserOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOutboxRepository extends JpaRepository<UserOutboxEntity, Long> {
}