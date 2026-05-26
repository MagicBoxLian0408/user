package kr.magicbox.user.application.port.in;

import kr.magicbox.user.application.dto.query.GetMyUserProfileQuery;
import kr.magicbox.user.application.dto.result.GetMyUserProfileResult;

public interface GetMyUserProfileUseCase {
    GetMyUserProfileResult getMyUserProfile(GetMyUserProfileQuery query);
}
