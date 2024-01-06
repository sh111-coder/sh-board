package com.shboard.shboard.global.session.domain;

import java.time.LocalDateTime;

import com.shboard.shboard.global.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSession extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;

    private String sessionValue;

    private LocalDateTime expiredDate;

    public MemberSession(final String sessionId, final String sessionValue) {
        this.sessionId = sessionId;
        this.sessionValue = sessionValue;
        this.expiredDate = LocalDateTime.now().plusHours(1L);
    }
}
