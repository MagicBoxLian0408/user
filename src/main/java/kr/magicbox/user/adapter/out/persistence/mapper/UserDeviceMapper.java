package kr.magicbox.user.adapter.out.persistence.mapper;

import kr.magicbox.user.domain.aggregate.UserDevice;
import kr.magicbox.user.adapter.out.persistence.entity.UserDeviceEntity;
import kr.magicbox.user.adapter.out.persistence.entity.UserEntity;
import kr.magicbox.user.adapter.out.persistence.entity.DeviceEntity;
import kr.magicbox.user.domain.vo.DeviceId;
import kr.magicbox.user.domain.vo.UserId;
import org.springframework.stereotype.Component;

@Component
public class UserDeviceMapper {

    public UserDeviceEntity toEntity(UserDevice userDevice, UserEntity userEntity, DeviceEntity deviceEntity) {
        UserDeviceEntity entity = UserDeviceEntity.builder()
                .user(userEntity)
                .device(deviceEntity)
                .build();
        
        if (!userDevice.isConnected()) {
            entity.updateFromDomain(userDevice);
        }
        
        return entity;
    }

    public UserDevice toDomain(UserDeviceEntity entity) {
        return UserDevice.reconstructBuilder()
                .id(entity.getId())
                .userId(UserId.of(entity.getUser().getId()))
                .deviceId(DeviceId.of(entity.getDevice().getId()))
                .isActive(entity.getIsActive())
                .build();
    }

    public void updateEntity(UserDevice userDevice, UserDeviceEntity entity) {
        entity.updateFromDomain(userDevice);
    }
}
