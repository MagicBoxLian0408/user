package kr.magicbox.user.application.port.in;

import kr.magicbox.user.application.dto.query.GetUserProfileQuery;
import kr.magicbox.user.application.dto.result.GetUserProfileResult;

public interface UserQueryUseCase {
    GetUserProfileResult getUserProfile(GetUserProfileQuery query);
}
