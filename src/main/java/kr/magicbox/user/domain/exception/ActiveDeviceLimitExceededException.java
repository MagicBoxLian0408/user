package kr.magicbox.user.domain.exception;

import kr.magicbox.user.domain.constants.UserPolicyConstants;
import kr.magicbox.user.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class ActiveDeviceLimitExceededException extends BusinessException {
    public ActiveDeviceLimitExceededException() {
        super("활성 기기 수 제한(" + UserPolicyConstants.maxActiveDevicesPerUser + "개)을 초과했습니다. 기존 기기에서 로그아웃 후 다시 시도해주세요.", HttpStatus.BAD_REQUEST);
    }
}
