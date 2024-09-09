package org.trandy.trandy_server.member.domain.converter;

import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.member.domain.Role;
import org.trandy.trandy_server.member.domain.dto.request.MemberCreateRequest;
import org.trandy.trandy_server.member.domain.dto.response.MemberInfoResponse;
import org.trandy.trandy_server.util.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// DTO - Entity 간 변환을 수행할 클래스
@Component
@RequiredArgsConstructor
public class MemberConverter {

    public MemberInfoResponse convertEntityToDto(Member member){
        return MemberInfoResponse.builder()
                .memberId(member.getMemberId())
                .build();
    }

    public Member convertDtoToEntity(MemberCreateRequest request) {
        return Member.builder()
                .memberId(request.getMemberId())
                .password(PasswordEncoderUtil.encodePassword(request.getPassword()))
                .memberName(request.getMemberName())
                .memberRole(Role.ADMIN.getRole().equals(request.getMemberRole()) ? Role.ADMIN : Role.USER)
                .email(request.getEmail())
                .deleted(Constants.DELETED_NOT)
                .build();
    }
}
