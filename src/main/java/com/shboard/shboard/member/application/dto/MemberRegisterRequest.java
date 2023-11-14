package com.shboard.shboard.member.application.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberRegisterRequest(
        @Size(min = 4, max = 12)
        String id,

        @Pattern(regexp = PASSWORD_REGEX)
        String password,

        @Size(min = 2, max = 10)
        String nickname
) {
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?,./\\\\-])[\\w!@#$%^&*()_+|~=`{}\\[\\]:\";'<>?,./\\\\-]{4,}$";
}
