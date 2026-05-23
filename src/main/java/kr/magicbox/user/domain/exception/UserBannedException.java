package kr.magicbox.user.domain.exception;

import kr.magicbox.user.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class UserBannedException extends BusinessException {
    public UserBannedException() {
        super("정지된 사용자는 로그인할 수 없습니다.", HttpStatus.FORBIDDEN);
    }
}