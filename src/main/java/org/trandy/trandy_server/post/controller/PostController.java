package org.trandy.trandy_server.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.ResponseDto;
import org.trandy.trandy_server.common.jwt.UserDetailsImpl;
import org.trandy.trandy_server.post.domain.dto.request.EnrollVoteRequest;
import org.trandy.trandy_server.post.service.PostService;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "투표 글 등록", description = "이미지 파일 업로드 및 투표글 생성")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @PostMapping(value = "/enrollVote", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto> enrollVote(@ModelAttribute @Valid EnrollVoteRequest request,
                                                    @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){

//        postService.enrollVote(request, userDetails.getMember());

        // 로그인 구현 전 개발용
        postService.enrollVote(request, 2);

        return ResponseEntity.ok(ResponseDto.success(Constants.API_RESPONSE_SUCCESSED));
    }
}
