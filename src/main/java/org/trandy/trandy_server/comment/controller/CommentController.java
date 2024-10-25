package org.trandy.trandy_server.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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

    @Operation(summary = "게시물에 투표 등록", description = "투표 게시물에 대한 투표 1개 생성")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @PostMapping(value = "/registerVoteComment")
    public ResponseEntity<ResponseDto> registerVoteComment(@RequestBody @Valid RegisterCommentRequest request,
                                                           @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){

//        postService.registerVoteComment(request, userDetails.getMember());

        // 로그인 구현 전 개발용
        commentService.registerVoteComment(request, 2);

        return ResponseEntity.ok(ResponseDto.success(Constants.API_RESPONSE_SUCCESSED));
    }

    @Operation(summary = "[게시물 상세] 게시물 내 투표 댓글 조회", description = "게시물 내 댓글 List 조회")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @GetMapping(value = "/retrieveVoteCommentList")
    public ResponseEntity<ResponseDto> retrieveVoteCommentList(@RequestParam @Parameter(description = "게시물 고유키", example = "1") long postId){

        return ResponseEntity.ok(commentService.retrieveVoteCommentList(postId));
    }

    @Operation(summary = "[마이페이지] 내가 작성한 댓글 조회", description = "내가 작성한 댓글 List조회")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @GetMapping(value = "/retrieveVoteCommentListByMemberId")
    public ResponseEntity<ResponseDto> retrieveVoteCommentListByMemberId(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){

//        commentService.retrieveVoteCommentListByMemberId(userDetails.getMember().getId());

        return ResponseEntity.ok(commentService.retrieveVoteCommentListByMemberId(2));
    }
}
