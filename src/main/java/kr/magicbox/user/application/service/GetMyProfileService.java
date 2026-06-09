package kr.magicbox.user.application.service;

import kr.magicbox.user.application.dto.result.GetUserProfileResult;
import kr.magicbox.user.application.dto.result.UserReviewResult;
import kr.magicbox.user.application.port.in.GetMyProfileUseCase;
import kr.magicbox.user.application.port.out.ReviewQueryPort;
import kr.magicbox.user.application.port.out.UserRepositoryPort;
import kr.magicbox.user.domain.aggregate.User;
import kr.magicbox.user.domain.exception.UserNotFoundException;
import kr.magicbox.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMyProfileService implements GetMyProfileUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final ReviewQueryPort reviewQueryPort;

    @Override
    @Transactional(readOnly = true)
    public GetUserProfileResult getMyProfile(UserId userId) {
        User user = userRepositoryPort.getUserById(userId)
                .orElseThrow(UserNotFoundException::new);

        List<UserReviewResult> reviews = user.canShowReview() ?
                reviewQueryPort.getAllReviewsByUserId(user.getId().value()) : Collections.emptyList();

        return GetUserProfileResult.builder()
                .profile(user.getProfile())
                .nickname(user.getNickname())
                .reviews(reviews)
                .role(user.getRole())
                .isMe(true)
                .build();
    }
}
