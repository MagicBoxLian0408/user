package kr.magicbox.user.domain.aggregate;

import kr.magicbox.user.domain.vo.DeviceId;
import kr.magicbox.user.domain.enums.DeviceType;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Device {
    private final DeviceId id;
    private final String deviceIdentifier;
    private final DeviceType deviceType;
    private String version;
    private Instant lastActiveAt;
    private Boolean isActive;

    @Builder(builderMethodName = "createBuilder", builderClassName = "CreateBuilder")
    public Device(String deviceIdentifier, DeviceType deviceType, String version) {
        this.id = null;
        this.deviceIdentifier = deviceIdentifier;
        this.deviceType = deviceType;
        this.version = version;
        this.isActive = false;
        this.lastActiveAt = Instant.now();
    }

    @Builder(builderMethodName = "reconstructBuilder", builderClassName = "ReconstructBuilder")
    public Device(DeviceId id, String deviceIdentifier, DeviceType deviceType, String version,
                  Boolean isActive, Instant lastActiveAt) {
        this.id = id;
        this.deviceIdentifier = deviceIdentifier;
        this.deviceType = deviceType;
        this.version = version;
        this.isActive = isActive != null ? isActive : false;
        this.lastActiveAt = lastActiveAt != null ? lastActiveAt : Instant.now();
    }

    public void updateVersion(String newVersion) {
        if (newVersion != null && !newVersion.trim().isEmpty()) {
            this.version = newVersion;
        }
    }

    public void updateLastActiveTime() {
        this.lastActiveAt = Instant.now();
    }

    public void activate() {
        this.isActive = true;
        this.lastActiveAt = Instant.now();
    }

    public void deactivate() {
        this.isActive = false;
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(this.isActive);
    }
}
