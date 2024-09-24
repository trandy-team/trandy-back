package org.trandy.trandy_server.member.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MemberAuthRequest {
    @Schema(description = "회원 아이디", example = "trandy@naver.com")
    private String email;

    @Schema(description = "회원 패스워드", example = "123123!")
    private String password;
}
