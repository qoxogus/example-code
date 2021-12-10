package com.cache.project.member;

import lombok.*;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class MemberDto {

    private String username;
    private String email;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .email(email)
                .build();
    }
}
