package com.shboard.shboard.member.presentation;

import java.net.URI;

import com.shboard.shboard.global.session.application.SessionService;
import com.shboard.shboard.global.session.application.dto.SessionCreateRequest;
import com.shboard.shboard.member.application.MemberService;
import com.shboard.shboard.member.application.dto.MemberLoginRequest;
import com.shboard.shboard.member.application.dto.MemberRegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;
    private final SessionService sessionService;

    @PostMapping("/register")
    private ResponseEntity<Void> register(@RequestBody @Valid final MemberRegisterRequest request) {
        final Long registeredId = memberService.register(request);
        return ResponseEntity
                .created(URI.create("/members/" + registeredId))
                .build();
    }

    @PostMapping("/login")
    private ResponseEntity<Void> login(@RequestBody final MemberLoginRequest request, final HttpServletRequest httpRequest) {
        final String memberLoginId = memberService.login(request);

        final HttpSession session = httpRequest.getSession();
        final SessionCreateRequest sessionCreateRequest = new SessionCreateRequest(session.getId(), memberLoginId);
        sessionService.create(sessionCreateRequest);

        return ResponseEntity.ok().build();
    }
}
