package kr.magicbox.user.application.service;

import kr.magicbox.user.application.port.in.GetUserNicknamesBatchUseCase;
import kr.magicbox.user.application.port.out.UserRepositoryPort;
import kr.magicbox.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetUserNicknamesBatchService implements GetUserNicknamesBatchUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> getUserNicknamesBatch(List<UserId> userIds) {
        List<Long> ids = userIds.stream().map(UserId::value).toList();
        return userRepositoryPort.getNicknamesByIds(ids);
    }
}
