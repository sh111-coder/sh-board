package com.shboard.shboard.global.auth;


import java.util.Arrays;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CookieManager {

    private static final String SESSION_KEY = "JSESSIONID";

    public String getSessionId(final HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            log.warn("Session Key Not Exists");
            throw new AuthException.FailAuthenticationMemberException();
        }

        final Cookie sessionCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(SESSION_KEY))
                .findFirst()
                .orElseGet(() -> {
                    log.warn("Session Key Not Exists");
                    throw new AuthException.FailAuthenticationMemberException();
                });

        return sessionCookie.getValue();
    }
}
