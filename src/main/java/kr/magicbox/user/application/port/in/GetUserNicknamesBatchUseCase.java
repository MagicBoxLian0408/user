package kr.magicbox.user.application.port.in;

import kr.magicbox.user.domain.vo.UserId;

import java.util.List;
import java.util.Map;

public interface GetUserNicknamesBatchUseCase {
    Map<Long, String> getUserNicknamesBatch(List<UserId> userIds);
}
