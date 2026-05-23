package kr.magicbox.user.adapter.out.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.magicbox.user.domain.aggregate.UserDevice;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "user_device",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "device_id"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDeviceEntity extends BaseEntity {

    @NotNull(message = "사용자는 필수입니다")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @NotNull(message = "디바이스는 필수입니다")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceEntity device;

    @NotNull(message = "활성 상태는 필수입니다")
    @Column(nullable = false)
    private Boolean isActive;

    @Builder
    public UserDeviceEntity(UserEntity user, DeviceEntity device) {
        this.user = user;
        this.device = device;
        this.isActive = false;
    }

    public void updateFromDomain(UserDevice userDevice) {
        this.isActive = userDevice.isConnected();
    }
}