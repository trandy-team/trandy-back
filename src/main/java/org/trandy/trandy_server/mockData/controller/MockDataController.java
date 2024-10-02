package org.trandy.trandy_server.mockData.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trandy.trandy_server.category.domain.Category;
import org.trandy.trandy_server.category.repository.CategoryRepository;
import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.ResponseDto;
import org.trandy.trandy_server.member.domain.Grade;
import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.member.domain.Role;
import org.trandy.trandy_server.member.repository.MemberRepository;
import org.trandy.trandy_server.util.PasswordEncoderUtil;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/mockdata")
@RequiredArgsConstructor
public class MockDataController {
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @PostMapping("/createMockData")
    public ResponseEntity<ResponseDto> createMockData(){
        try{
            List<Member> members = new ArrayList<>();

            // Admin 계정
            members.add(Member.builder()
                    .email("admin@naver.com")
                    .password(PasswordEncoderUtil.encodePassword("123123!"))
                    .nickname("superuser")
                    .memberRole(Role.ADMIN)
                    .grade(Grade.DIAMOND)
                    .build());

            // 일반 계정
            members.add(Member.builder()
                    .email("trandy@naver.com")
                    .password(PasswordEncoderUtil.encodePassword("123123!"))
                    .nickname("user")
                    .memberRole(Role.USER)
                    .grade(Grade.BRONZE)
                    .build());

            memberRepository.saveAll(members);

            // 카테고리
            List<Category> categories = new ArrayList<>();
            categories.add(Category.builder().categoryName("패션").build());
            categories.add(Category.builder().categoryName("음악").build());
            categories.add(Category.builder().categoryName("아이돌").build());
            categories.add(Category.builder().categoryName("밈").build());

            categoryRepository.saveAll(categories);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("목데이터 생성 중 오류 발생");
        }

        return ResponseEntity.ok(ResponseDto.success(Constants.API_RESPONSE_SUCCESSED));
    }
}
