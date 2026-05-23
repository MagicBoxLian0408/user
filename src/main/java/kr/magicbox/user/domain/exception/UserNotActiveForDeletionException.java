package kr.magicbox.user.domain.exception;

import kr.magicbox.user.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class UserNotActiveForDeletionException extends BusinessException {
    public UserNotActiveForDeletionException() {
        super("활성 상태가 아닌 사용자는 탈퇴 처리할 수 없습니다.", HttpStatus.CONFLICT);
    }
}
