package com.shboard.shboard.session.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSessionRepository extends JpaRepository<MemberSession, Long> {
}
