package kr.magicbox.user.adapter.in.grpc.exception;

import kr.magicbox.user.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

@SuppressWarnings("java:S110")
public class UnsupportedOAuth2ProviderException extends BusinessException {
    public UnsupportedOAuth2ProviderException(String provider) {
        super(provider + " OAuth2는 지원하지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}
