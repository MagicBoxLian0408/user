package kr.magicbox.user.domain.exception;

import kr.magicbox.user.domain.vo.Nickname;
import kr.magicbox.user.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class DuplicateNicknameException extends BusinessException {
    public DuplicateNicknameException(Nickname nickname) {
        super("닉네임 " + nickname.value() + " 은 이미 사용 중입니다.", HttpStatus.CONFLICT);
    }
}