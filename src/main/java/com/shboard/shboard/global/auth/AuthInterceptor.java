package com.shboard.shboard.global.auth;

import java.util.Arrays;

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

    private static final String SESSION_KEY = "SESSION";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        checkSessionExist(request);

        return true;
    }

    private void checkSessionExist(final HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            log.warn("Session Key Not Exists");
            throw new AuthException.FailAuthenticationMemberException();
        }

        Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(SESSION_KEY))
                .findFirst()
                .orElseGet(() -> {
                    log.warn("Session Key Not Exists");
                    throw new AuthException.FailAuthenticationMemberException();
                });
    }
}
