package kr.magicbox.user.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "user")
public class UserProperties {

    private final String defaultProfileImageUrl;
}