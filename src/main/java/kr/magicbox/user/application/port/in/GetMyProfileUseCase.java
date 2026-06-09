package kr.magicbox.user.application.port.in;

import kr.magicbox.user.application.dto.result.GetUserProfileResult;
import kr.magicbox.user.domain.vo.UserId;

public interface GetMyProfileUseCase {
    GetUserProfileResult getMyProfile(UserId userId);
}
