package com.shboard.shboard.member.application;

import com.shboard.shboard.member.application.dto.MemberRegisterRequest;
import com.shboard.shboard.member.domain.Member;
import com.shboard.shboard.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long register(final MemberRegisterRequest request) {
        final Member member = Member.builder()
                .loginId(request.id())
                .password(request.password())
                .nickname(request.nickname())
                .build();

        final Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }
}