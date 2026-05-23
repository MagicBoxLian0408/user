package kr.magicbox.user.application.port.out;

import kr.magicbox.user.application.dto.result.UserReviewResult;

import java.util.List;


public interface ReviewQueryPort {
    List<UserReviewResult> getAllReviewsByUserId(Long userId);
}
