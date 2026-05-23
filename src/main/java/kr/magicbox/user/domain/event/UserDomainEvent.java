package kr.magicbox.user.domain.event;

public interface UserDomainEvent {
    String key();
    UserDomainEventType eventType();
}