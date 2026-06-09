package kr.magicbox.user.adapter.in.web;

import kr.magicbox.user.adapter.in.web.dto.response.GetUserProfileResponse;
import kr.magicbox.user.application.dto.result.GetUserProfileResult;
import kr.magicbox.user.application.port.in.GetMyProfileUseCase;
import kr.magicbox.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MeQueryController {

    private final GetMyProfileUseCase getMyProfileUseCase;

    @GetMapping
    public ResponseEntity<GetUserProfileResponse> getMyProfile(
            @AuthenticationPrincipal UserId userId) {
        GetUserProfileResult result = getMyProfileUseCase.getMyProfile(userId);
        return ResponseEntity.ok(GetUserProfileResponse.builder()
                .nickname(result.nickname())
                .profile(result.profile())
                .reviews(result.reviews())
                .role(result.role())
                .isMe(result.isMe())
                .build());
    }
}
