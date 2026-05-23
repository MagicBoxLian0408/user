package kr.magicbox.user.domain.vo;

import kr.magicbox.user.domain.constants.UserPolicyConstants;
import kr.magicbox.user.domain.exception.InvalidFieldException;

public record Nickname(String value) {

    public Nickname {
        validateNickname(value);
        value = value.trim();
    }

    public static Nickname of(String value) {
        return new Nickname(value);
    }

    private static void validateNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new InvalidFieldException("닉네임은 필수 값입니다.");
        }

        String trimmedNickname = nickname.trim();
        
        if (trimmedNickname.length() < UserPolicyConstants.nicknameMinLength) {
            throw new InvalidFieldException("닉네임은 " + UserPolicyConstants.nicknameMinLength + "자 이상이어야 합니다.");
        }

        if (trimmedNickname.length() > UserPolicyConstants.nicknameMaxLength) {
            throw new InvalidFieldException("닉네임은 " + UserPolicyConstants.nicknameMaxLength + "자 이내여야 합니다.");
        }

        if (!isValidNicknamePattern(trimmedNickname)) {
            throw new InvalidFieldException("닉네임은 한글, 영문, 숫자만 사용할 수 있습니다.");
        }
    }

    private static boolean isValidNicknamePattern(String nickname) {
        return nickname.matches("^[가-힣a-zA-Z0-9]+$");
    }
}
