package com.shboard.shboard.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.shboard.shboard.member.application.dto.MemberRegisterRequest;
import com.shboard.shboard.member.common.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberServiceTest extends ServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입에 성공한다.")
    void success_save() {
        // given
        final String loginId = "sh111";
        final String password = "password1!";
        final String nickname = "seongha";
        final MemberRegisterRequest request = new MemberRegisterRequest(loginId, password, nickname);

        // when
        final Long registeredMemberId = memberService.register(request);

        // then
        assertThat(registeredMemberId).isNotNull();
    }
}
