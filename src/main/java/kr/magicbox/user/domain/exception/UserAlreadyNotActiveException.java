package kr.magicbox.user.domain.exception;

import kr.magicbox.user.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class UserAlreadyNotActiveException extends BusinessException {
    public UserAlreadyNotActiveException() {
        super("이미 비활성 상태인 사용자입니다.", HttpStatus.CONFLICT);
    }
}
