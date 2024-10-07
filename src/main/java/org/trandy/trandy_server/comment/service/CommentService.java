package org.trandy.trandy_server.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trandy.trandy_server.comment.domain.VoteComment;
import org.trandy.trandy_server.comment.domain.VotePosition;
import org.trandy.trandy_server.comment.domain.request.RegisterCommentRequest;
import org.trandy.trandy_server.comment.repository.CommentRepository;
import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.ResponseDto;
import org.trandy.trandy_server.exception.CustomException;
import org.trandy.trandy_server.exception.ExceptionStatus;
import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.member.service.MemberService;
import org.trandy.trandy_server.post.domain.Post;
import org.trandy.trandy_server.post.service.PostService;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberService memberService;
    private final PostService postService;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseDto registerVoteComment(RegisterCommentRequest request, long memberId) {
        // 멤버 객체 영속화 (개발용)
        Member member = memberService.retrieveMemberMockData(memberId);

        // Post 객체 영속화
        Post post = postService.retrievePostById(request.getPostId());

        // 이미 투표를 진행한 회원인지 확인
        commentRepository.findByPostIdAndMemberId(post.getId(), member.getId()).ifPresent(comment -> {
                    throw new CustomException(ExceptionStatus.CommentAlreadyResisteredException);
                });

        commentRepository.save(VoteComment.builder()
                        .votePosition(request.getVotePosition().equals(VotePosition.AGREE.getDesc())
                                ? VotePosition.AGREE
                                : VotePosition.DISAGREE
                        )
                        .contents(request.getContents())
                        .member(member)
                        .post(post)
                .build());

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }
}
