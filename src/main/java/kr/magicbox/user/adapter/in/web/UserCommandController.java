package kr.magicbox.user.adapter.in.web;

import jakarta.validation.Valid;
import kr.magicbox.user.adapter.in.web.dto.request.UpdateUserProfileRequest;
import kr.magicbox.user.application.dto.command.WithdrawUserCommand;
import kr.magicbox.user.application.port.in.UserCommandUseCase;
import kr.magicbox.user.application.port.in.WithdrawUserUseCase;
import kr.magicbox.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class UserCommandController {

    private final UserCommandUseCase userCommandUseCase;
    private final WithdrawUserUseCase withdrawUserUseCase;

    @PatchMapping
    public ResponseEntity<Void> updateUserProfile(@AuthenticationPrincipal UserId userId,
                                                  @RequestBody @Valid UpdateUserProfileRequest request
    ) {
        userCommandUseCase.updateUserProfile(request.toCommand(userId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> withdraw(@AuthenticationPrincipal UserId userId) {
        withdrawUserUseCase.withdrawUser(WithdrawUserCommand.of(userId));
        return ResponseEntity.noContent().build();
    }
}
