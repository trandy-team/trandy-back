package org.trandy.trandy_server.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trandy.trandy_server.comment.domain.request.RegisterCommentRequest;
import org.trandy.trandy_server.comment.service.CommentService;
import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.ResponseDto;
import org.trandy.trandy_server.common.jwt.UserDetailsImpl;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "투표 댓글 등록", description = "투표 게시물에 대한 투표 1개 생성")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @PostMapping(value = "/registerVoteComment")
    public ResponseEntity<ResponseDto> registerVoteComment(@RequestBody @Valid RegisterCommentRequest request,
                                                           @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){

//        postService.registerVoteComment(request, userDetails.getMember());

        // 로그인 구현 전 개발용
        commentService.registerVoteComment(request, 2);

        return ResponseEntity.ok(ResponseDto.success(Constants.API_RESPONSE_SUCCESSED));
    }
}
