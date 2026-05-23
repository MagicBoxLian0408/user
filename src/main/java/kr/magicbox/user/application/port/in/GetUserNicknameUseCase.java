package kr.magicbox.user.application.port.in;

import kr.magicbox.user.domain.vo.UserId;

public interface GetUserNicknameUseCase {
    String getUserNickname(UserId userId);
}
