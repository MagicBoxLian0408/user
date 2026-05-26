package kr.magicbox.user.adapter.in.web;

import jakarta.validation.constraints.NotNull;
import kr.magicbox.user.adapter.in.web.dto.response.GetMyUserProfileResponse;
import kr.magicbox.user.adapter.in.web.dto.response.GetUserProfileResponse;
import kr.magicbox.user.application.dto.query.GetMyUserProfileQuery;
import kr.magicbox.user.application.dto.query.GetUserProfileQuery;
import kr.magicbox.user.application.dto.result.GetMyUserProfileResult;
import kr.magicbox.user.application.dto.result.GetUserProfileResult;
import kr.magicbox.user.application.port.in.GetMyUserProfileUseCase;
import kr.magicbox.user.application.port.in.UserQueryUseCase;
import kr.magicbox.user.domain.vo.Nickname;
import kr.magicbox.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
public class UserQueryController {
    private final UserQueryUseCase userQueryUseCase;
    private final GetMyUserProfileUseCase getMyUserProfileUseCase;

    @GetMapping("/user/profile/{nickname}")
    public ResponseEntity<GetUserProfileResponse> getUserProfile(
            @PathVariable @NotNull(message = "닉네임은 필수 값입니다.") String nickname,
            @AuthenticationPrincipal UserId requestUserId) {
        GetUserProfileResult result = userQueryUseCase.getUserProfile(GetUserProfileQuery.of(Nickname.of(nickname), requestUserId));
        return ResponseEntity.ok(GetUserProfileResponse.builder()
                .nickname(result.nickname())
                .profile(result.profile())
                .reviews(result.reviews())
                .role(result.role())
                .isMe(result.isMe())
                .build());
    }

    @GetMapping("/user/me")
    public ResponseEntity<GetMyUserProfileResponse> getMyUserProfile(
            @AuthenticationPrincipal UserId userId) {
        GetMyUserProfileResult result = getMyUserProfileUseCase.getMyUserProfile(GetMyUserProfileQuery.of(userId));
        return ResponseEntity.ok(GetMyUserProfileResponse.builder()
                .id(result.id())
                .nickname(result.nickname())
                .profile(result.profile())
                .role(result.role())
                .build());
    }
}
