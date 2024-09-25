package org.trandy.trandy_server.vote.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.ResponseDto;
import org.trandy.trandy_server.vote.service.PostService;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/enrollVote")
    public ResponseEntity<ResponseDto> enrollVote(){

        postService.enrollVote();

        return ResponseEntity.ok(ResponseDto.success(Constants.API_RESPONSE_SUCCESSED));
    }
}
