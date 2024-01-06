package com.shboard.shboard.global.session.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSessionRepository extends JpaRepository<MemberSession, Long> {

    boolean existsBySessionId(final String sessionId);

    Optional<MemberSession> findBySessionId(final String sessionId);
}
