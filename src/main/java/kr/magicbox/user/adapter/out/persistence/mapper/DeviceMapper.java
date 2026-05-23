package kr.magicbox.user.adapter.out.persistence.mapper;

import kr.magicbox.user.domain.aggregate.Device;
import kr.magicbox.user.adapter.out.persistence.entity.DeviceEntity;
import kr.magicbox.user.domain.vo.DeviceId;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    public DeviceEntity toEntity(Device device) {
        return DeviceEntity.builder()
                .deviceIdentifier(device.getDeviceIdentifier())
                .deviceType(device.getDeviceType())
                .version(device.getVersion())
                .build();
    }

    public Device toDomain(DeviceEntity entity) {
        return Device.reconstructBuilder()
                .id(DeviceId.of(entity.getId()))
                .deviceIdentifier(entity.getDeviceIdentifier())
                .deviceType(entity.getDeviceType())
                .version(entity.getVersion())
                .isActive(entity.getIsActive())
                .lastActiveAt(entity.getLastActiveAt())
                .build();
    }

    public void updateEntity(Device device, DeviceEntity entity) {
        entity.updateFromDomain(device);
    }
}
