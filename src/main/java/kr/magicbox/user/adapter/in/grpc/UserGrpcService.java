package kr.magicbox.user.adapter.in.grpc;

import kr.magicbox.user.adapter.in.grpc.exception.UnsupportedOAuth2ProviderException;
import kr.magicbox.user.application.dto.query.CheckUserActiveQuery;
import kr.magicbox.user.application.dto.command.LoadUserCredentialCommand;
import kr.magicbox.user.application.dto.result.LoadUserCredentialResult;
import kr.magicbox.user.application.port.in.CheckUserActiveUseCase;
import kr.magicbox.user.application.port.in.GetUserNicknameUseCase;
import kr.magicbox.user.application.port.in.GetUserNicknamesBatchUseCase;
import kr.magicbox.user.application.port.in.GetUserProfileImageUrlUseCase;
import kr.magicbox.user.application.port.in.LoadUserCredentialUseCase;
import kr.magicbox.user.domain.enums.OAuth2Provider;
import kr.magicbox.user.domain.enums.UserRole;
import kr.magicbox.user.domain.vo.UserId;
import kr.magicbox.user.grpc.user.*;

import java.util.List;
import java.util.Map;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    private final LoadUserCredentialUseCase loadUserCredentialUseCase;
    private final CheckUserActiveUseCase checkUserActiveUseCase;
    private final GetUserNicknameUseCase getUserNicknameUseCase;
    private final GetUserNicknamesBatchUseCase getUserNicknamesBatchUseCase;
    private final GetUserProfileImageUrlUseCase getUserProfileImageUrlUseCase;

    @Override
    public void loadUserCredential(LoadUserCredentialRequest request,
                                   StreamObserver<LoadUserCredentialResponse> responseObserver) {
        LoadUserCredentialCommand command = LoadUserCredentialCommand.of(
                request.getOauth2Id(),
                toOAuth2Provider(request.getProvider()),
                request.getEmail(),
                request.getProfileImage()
        );

        LoadUserCredentialResult result = loadUserCredentialUseCase.loadUserCredential(command);

        responseObserver.onNext(LoadUserCredentialResponse.newBuilder()
                .setUserId(result.userId().value())
                .setUserRole(toGrpcUserRole(result.userRole()))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void checkUserActive(CheckUserActiveRequest request,
                                StreamObserver<CheckUserActiveResponse> responseObserver) {
        boolean active = checkUserActiveUseCase.isActive(CheckUserActiveQuery.of(UserId.of(request.getUserId())));

        responseObserver.onNext(CheckUserActiveResponse.newBuilder()
                .setActive(active)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getUserNickname(GetUserNicknameRequest request,
                                StreamObserver<GetUserNicknameResponse> responseObserver) {
        String nickname = getUserNicknameUseCase.getUserNickname(UserId.of(request.getUserId()));

        responseObserver.onNext(GetUserNicknameResponse.newBuilder()
                .setNickname(nickname)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getUserNicknamesBatch(GetUserNicknamesBatchRequest request,
                                      StreamObserver<GetUserNicknamesBatchResponse> responseObserver) {
        List<UserId> userIds = request.getUserIdsList().stream()
                .map(UserId::of)
                .toList();
        Map<Long, String> nicknames = getUserNicknamesBatchUseCase.getUserNicknamesBatch(userIds);

        responseObserver.onNext(GetUserNicknamesBatchResponse.newBuilder()
                .putAllNicknames(nicknames)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getUserProfileImageUrl(GetUserProfileImageUrlRequest request,
                                       StreamObserver<GetUserProfileImageUrlResponse> responseObserver) {
        String profileImageUrl = getUserProfileImageUrlUseCase.getUserProfileImageUrl(UserId.of(request.getUserId()));

        responseObserver.onNext(GetUserProfileImageUrlResponse.newBuilder()
                .setProfileImageUrl(profileImageUrl != null ? profileImageUrl : "")
                .build());
        responseObserver.onCompleted();
    }

    private GrpcUserRole toGrpcUserRole(UserRole userRole) {
        return switch (userRole) {
            case USER -> GrpcUserRole.USER;
            case CREATOR -> GrpcUserRole.CREATOR;
            case ADMIN -> GrpcUserRole.ADMIN;
        };
    }

    private OAuth2Provider toOAuth2Provider(GrpcOAuth2Provider grpcProvider) {
        return switch (grpcProvider) {
            case GOOGLE -> OAuth2Provider.GOOGLE;
            case NAVER -> OAuth2Provider.NAVER;
            case KAKAO -> OAuth2Provider.KAKAO;
            default -> throw new UnsupportedOAuth2ProviderException(grpcProvider.name());
        };
    }
}
