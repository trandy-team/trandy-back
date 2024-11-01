package org.trandy.trandy_server.member.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.trandy.trandy_server.member.domain.Role;

@Getter
public class MemberCreateRequest {

//    @Schema(description = "회원 아이디", example = "testUser")
//    @Size(min = 8, message = "유저 아이디는 최소 8자 이상이어야 합니다.")
//    @NotNull
//    private String memberId;

    @Schema(description = "이메일", example = "trandy@naver.com")
    @Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"
            ,message = "이메일 형식이 유효하지 않습니다.")
    @NotNull
    private String email;

    @Schema(description = "회원 패스워드", example = "123123!")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @NotNull
    private String password;

    @Schema(description = "닉네임", example = "trandyuser")
    @Size(min = 8, max = 20, message = "닉네임은 8 ~ 20 자 입니다.")
    @NotNull
    private String nickname;

    @Schema(description = "권한", example = "ADMIN", allowableValues = "ADMIN, USER")
    @Pattern(regexp = "^(ADMIN|USER)$", message = "ADMIN 혹은 USER 로 입력하세요")
    @NotNull
    private Role memberRole;

}
