package com.shboard.shboard.global.auth;

import com.shboard.shboard.global.session.domain.MemberSessionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final CookieManager cookieManager;

    private final MemberSessionRepository memberSessionRepository;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String sessionId = cookieManager.getSessionId(request);
        if (!memberSessionRepository.existsBySessionId(sessionId)) {
            log.warn("Session Key exists, but Session ID is not exists");
            throw new AuthException.FailAuthenticationMemberException();
        }

        return true;
    }
}
