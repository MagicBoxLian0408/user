package kr.magicbox.user.domain.service;

import kr.magicbox.user.domain.constants.UserPolicyConstants;
import kr.magicbox.user.domain.exception.ActiveDeviceLimitExceededException;
import org.springframework.stereotype.Service;

@Service
public class DeviceDomainService {

    public void validateUserDevicePolicy(Integer currentActiveDevices) {
        if(currentActiveDevices + 1 > UserPolicyConstants.maxActiveDevicesPerUser)
            throw new ActiveDeviceLimitExceededException();
    }

}