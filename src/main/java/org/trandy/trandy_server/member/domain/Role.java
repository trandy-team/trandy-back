package org.trandy.trandy_server.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ADMIN", "관리자"),
    USER("USER", "일반 유저");

    private final String role;
    private final String description;
}
