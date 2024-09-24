package org.trandy.trandy_server.mockData.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.ResponseDto;
import org.trandy.trandy_server.member.domain.Grade;
import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.member.domain.Role;
import org.trandy.trandy_server.member.repository.MemberRepository;
import org.trandy.trandy_server.util.PasswordEncoderUtil;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/mockdata")
@RequiredArgsConstructor
public class MockDataController {
    private final MemberRepository memberRepository;

    @PostMapping("/createMockData")
    public ResponseEntity<ResponseDto> createMockData(){
        try{
            memberRepository.save(Member.builder()
                            .email("skehrhks@naver.com")
                            .password(PasswordEncoderUtil.encodePassword("123123"))
                            .nickname("admin")
                            .memberRole(Role.ADMIN)
                            .grade(Grade.DIAMOND)
                            .deleted(Constants.DELETED_NOT)
                            .deletedAt(LocalDateTime.now())
                    .build());

        }catch (Exception e){
            throw new RuntimeException("목데이터 생성 중 오류 발생");
        }

        return ResponseEntity.ok(ResponseDto.success(Constants.API_RESPONSE_SUCCESSED));
    }
}
