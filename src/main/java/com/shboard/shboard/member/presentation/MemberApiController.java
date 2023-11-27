package com.shboard.shboard.member.presentation;

import java.net.URI;

import com.shboard.shboard.member.application.MemberService;
import com.shboard.shboard.member.application.dto.MemberRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/register")
    private ResponseEntity<Void> register(@RequestBody @Valid final MemberRegisterRequest request) {
        final Long registeredId = memberService.register(request);
        return ResponseEntity
                .created(URI.create("/members/" + registeredId))
                .build();
    }
}
