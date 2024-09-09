package org.trandy.trandy_server.member.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MemberAuthRequest {
    @Schema(description = "회원 아이디", example = "testUser")
    private String memberId;

    @Schema(description = "회원 패스워드", example = "password123!")
    private String password;
}
