package kr.magicbox.user.application.port.in;

import kr.magicbox.user.application.dto.query.CheckUserActiveQuery;

public interface CheckUserActiveUseCase {
    boolean isActive(CheckUserActiveQuery query);
}
