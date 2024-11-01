package org.trandy.trandy_server.member.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberUpdateRequest {
    @Schema(description = "회원 아이디", example = "testUser")
    @NotNull
    private String memberId;

    @Schema(description = "회원 패스워드", example = "password123!")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @NotNull
    private String password;

    @Schema(description = "회원 명", example = "아무개123")
    @Size(max = 8, message = "이름은 최소 20자 이하여야 합니다.")
    @NotNull
    private String memberName;

    @Schema(description = "권한", example = "USER / ADMIN")
    @Pattern(regexp = "^(ADMIN|USER)$", message = "ADMIN 혹은 USER 로 입력하세요")
    @NotNull
    private String memberRole;

    @Schema(description = "이메일", example = "abcdd@naver.com")
    @Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"
            ,message = "이메일 형식이 유효하지 않습니다.")
    @NotNull
    private String email;

}
