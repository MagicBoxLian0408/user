package kr.magicbox.user.domain.exception;

import kr.magicbox.user.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class InvalidUserRoleException extends BusinessException {
    public InvalidUserRoleException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}