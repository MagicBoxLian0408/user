package kr.magicbox.user.domain.exception;

import kr.magicbox.user.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class UserAlreadyBannedException extends BusinessException {
    public UserAlreadyBannedException() {
        super("이미 정지된 사용자입니다.", HttpStatus.CONFLICT);
    }
}
