package com.shboard.shboard.global.auth;

import java.util.Arrays;

import com.shboard.shboard.session.domain.MemberSessionRepository;
import jakarta.servlet.http.Cookie;
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

    private static final String SESSION_KEY = "JSESSIONID";

    private final MemberSessionRepository memberSessionRepository;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final Cookie[] cookies = request.getCookies();

        final Cookie sessionCookie = getSessionFromCookie(cookies);
        if (!memberSessionRepository.existsBySessionId(sessionCookie.getValue())) {
            log.warn("Session Key exists, but Session ID is not exists");
            throw new AuthException.FailAuthenticationMemberException();
        }

        return true;
    }

    private Cookie getSessionFromCookie(final Cookie[] cookies) {
        if (cookies == null) {
            log.warn("Session Key Not Exists");
            throw new AuthException.FailAuthenticationMemberException();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(SESSION_KEY))
                .findFirst()
                .orElseGet(() -> {
                    log.warn("Session Key Not Exists");
                    throw new AuthException.FailAuthenticationMemberException();
                });
    }
}
