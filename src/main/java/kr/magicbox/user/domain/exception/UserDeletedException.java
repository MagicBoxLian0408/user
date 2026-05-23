package kr.magicbox.user.domain.exception;

import kr.magicbox.user.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class UserDeletedException extends BusinessException {
    public UserDeletedException() {
        super("탈퇴한 사용자는 로그인할 수 없습니다.", HttpStatus.FORBIDDEN);
    }
}