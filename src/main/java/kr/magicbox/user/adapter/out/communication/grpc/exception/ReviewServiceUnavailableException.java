package kr.magicbox.user.adapter.out.communication.grpc.exception;

import kr.magicbox.user.global.exception.SystemError;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class ReviewServiceUnavailableException extends SystemError {
    public ReviewServiceUnavailableException(Throwable cause) {
        super("리뷰 서비스 호출을 할 수 없습니다", HttpStatus.SERVICE_UNAVAILABLE, cause);
    }
}