package kr.magicbox.user.application.dto.command;

import kr.magicbox.user.domain.enums.OAuth2Provider;

public record LoadUserCredentialCommand(
        String oauth2Id,
        OAuth2Provider provider,
        String email,
        String profileImage
) {
    public static LoadUserCredentialCommand of(String oauth2Id, OAuth2Provider provider, String email, String profileImage) {
        return new LoadUserCredentialCommand(
                oauth2Id,
                provider,
                email,
                profileImage != null && !profileImage.isBlank() ? profileImage : null
        );
    }
}
