package com.shboard.shboard.global.auth;

import com.shboard.shboard.global.session.domain.MemberSession;
import com.shboard.shboard.global.session.domain.MemberSessionRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final CookieManager cookieManager;
    private final MemberSessionRepository memberSessionRepository;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMember.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final String sessionId = cookieManager.getSessionId(webRequest.getNativeRequest(HttpServletRequest.class));
        final MemberSession memberSession = memberSessionRepository.findBySessionId(sessionId).get();

        return new AuthMemberId(memberSession.getSessionValue());
    }
}
