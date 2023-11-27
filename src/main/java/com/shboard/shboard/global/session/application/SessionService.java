package com.shboard.shboard.global.session.application;

import com.shboard.shboard.global.session.application.dto.SessionCreateRequest;
import com.shboard.shboard.global.session.domain.MemberSession;
import com.shboard.shboard.global.session.domain.MemberSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SessionService {

    private final MemberSessionRepository memberSessionRepository;

    public void create(final SessionCreateRequest request) {
        final String sessionId = request.sessionId();
        final String sessionValue = request.sessionValue();
        final MemberSession memberSession = new MemberSession(sessionId, sessionValue);
        final MemberSession savedMemberSession = memberSessionRepository.save(memberSession);

        log.info("Create Session Success! Session Id = {}", savedMemberSession.getSessionId());
    }
}
