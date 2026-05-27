package kr.magicbox.user.adapter.in.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.magicbox.user.adapter.in.security.properties.TrustedIpProperties;
import kr.magicbox.user.domain.vo.UserId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserInfoExtractFilter extends OncePerRequestFilter {

    private final TrustedIpProperties trustedIpProperties;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String clientIp = request.getRemoteAddr();
        log.debug("[UserInfoExtractFilter] clientIp={}, trusted={}", clientIp, trustedIpProperties.getIps().contains(clientIp));

        if (!trustedIpProperties.getIps().contains(clientIp)) {
            filterChain.doFilter(request, response);
            return;
        }

        String userIdRequestHeader = request.getHeader("X-User-Id");

        if (!isValidUserId(userIdRequestHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userIdLong = Long.valueOf(userIdRequestHeader);
        UserId userId = UserId.of(userIdLong);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private boolean isValidUserId(String userIdRequestHeader) {
        try {
            return Long.parseLong(userIdRequestHeader) > 0;
        }
        catch (Exception e) {
            return false;
        }
    }
}