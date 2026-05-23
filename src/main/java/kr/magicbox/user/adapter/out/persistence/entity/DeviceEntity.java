package kr.magicbox.user.adapter.out.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.magicbox.user.domain.enums.DeviceType;
import kr.magicbox.user.domain.aggregate.Device;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "device")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DeviceEntity extends BaseEntity {

    @NotBlank(message = "디바이스 식별번호는 필수입니다")
    @Column(unique = true, nullable = false, updatable = false)
    private String deviceIdentifier;

    @NotNull(message = "디바이스 타입은 필수입니다")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private DeviceType deviceType;

    @NotBlank(message = "앱 버전은 필수입니다")
    @Column(nullable = false)
    private String version;

    @Column
    private Instant lastActiveAt;

    @NotNull(message = "활성 상태는 필수입니다")
    @Column(nullable = false)
    private Boolean isActive;

    @Builder
    public DeviceEntity(String deviceIdentifier, DeviceType deviceType, String version) {
        this.deviceIdentifier = deviceIdentifier;
        this.deviceType = deviceType;
        this.version = version;
        this.isActive = false;
        this.lastActiveAt = Instant.now();
    }

    public void updateFromDomain(Device device) {
        this.version = device.getVersion();
        this.lastActiveAt = device.getLastActiveAt();
        this.isActive = device.isActive();
    }

}