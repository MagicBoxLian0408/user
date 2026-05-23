package kr.magicbox.user.adapter.in.web;

import kr.magicbox.user.application.dto.command.BanUserCommand;
import kr.magicbox.user.application.dto.command.UnbanUserCommand;
import kr.magicbox.user.application.port.in.BanUserUseCase;
import kr.magicbox.user.application.port.in.UnbanUserUseCase;
import kr.magicbox.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserCommandController {

    private final BanUserUseCase banUserUseCase;
    private final UnbanUserUseCase unbanUserUseCase;

    @PatchMapping("/{userId}/ban")
    public ResponseEntity<Void> banUser(@PathVariable Long userId) {
        banUserUseCase.banUser(BanUserCommand.of(UserId.of(userId)));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/unban")
    public ResponseEntity<Void> unbanUser(@PathVariable Long userId) {
        unbanUserUseCase.unbanUser(UnbanUserCommand.of(UserId.of(userId)));
        return ResponseEntity.noContent().build();
    }
}
