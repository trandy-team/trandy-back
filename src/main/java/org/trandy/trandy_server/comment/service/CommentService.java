package org.trandy.trandy_server.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trandy.trandy_server.comment.domain.VoteComment;
import org.trandy.trandy_server.comment.domain.VotePosition;
import org.trandy.trandy_server.comment.domain.converter.CommentConverter;
import org.trandy.trandy_server.comment.domain.request.RegisterCommentRequest;
import org.trandy.trandy_server.comment.domain.request.UpdateCommentRequest;
import org.trandy.trandy_server.comment.domain.response.CommentByIdResponse;
import org.trandy.trandy_server.comment.repository.CommentRepository;
import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.ResponseDto;
import org.trandy.trandy_server.exception.CustomException;
import org.trandy.trandy_server.exception.ExceptionStatus;
import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.member.service.MemberService;
import org.trandy.trandy_server.post.domain.Post;
import org.trandy.trandy_server.post.service.PostService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberService memberService;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;

    @Transactional
    public ResponseDto registerVoteComment(RegisterCommentRequest request, long memberId) {
        // 멤버 객체 영속화 (개발용)
        Member member = memberService.retrieveMemberMockData(memberId);

        // Post 객체 영속화
        Post post = postService.retrievePostById(request.getPostId());

        // 이미 투표를 진행한 회원인지 확인
        commentRepository.findByPostIdAndMemberId(post.getId(), member.getId()).ifPresent(comment -> {
                    throw new CustomException(ExceptionStatus.VoteAlreadyResisteredException);
                });

        commentRepository.save(VoteComment.builder()
                        .votePosition(request.getVotePosition().equals(VotePosition.AGREE.getDesc())
                                ? VotePosition.AGREE
                                : VotePosition.DISAGREE
                        )
                        .member(member)
                        .post(post)
                .build());

        // VoteCount 증가
        postService.increaseVoteCount(post);

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }

    @Transactional
    public ResponseDto updateVoteComment(UpdateCommentRequest request, int memberId) {
        VoteComment comment = commentRepository.findByPostIdAndMemberId(request.getPostId(), memberId).orElseThrow(
                () -> new CustomException(ExceptionStatus.DataNotFoundException));

        if(comment.getContents() != null && comment.getContents().isEmpty()){
            throw new CustomException(ExceptionStatus.CommentAlreadyResisteredException);
        }

        comment.updateComment(request.getContents());

        commentRepository.save(comment);

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }

    @Transactional(readOnly = true)
    public ResponseDto retrieveVoteCommentList(long postId) {
        List<VoteComment> comments = commentRepository.findByPostIdAndContentsIsNotNullAndContentsNot(postId, "");
        List<CommentByIdResponse> responses;

        if(!comments.isEmpty()){
            responses = commentConverter.entityListToDtoList(comments);
        }else{
            throw new CustomException(ExceptionStatus.DataNotFoundException);
        }

        return ResponseDto.success(responses);
    }

    @Transactional(readOnly = true)
    public ResponseDto retrieveVoteCommentListByMemberId(long memberId) {
        List<VoteComment> comments = commentRepository.findByMemberId(memberId);
        List<CommentByIdResponse> responses;

        if(!comments.isEmpty()){
            responses = commentConverter.entityListToDtoList(comments);
        }else{
            throw new CustomException(ExceptionStatus.DataNotFoundException);
        }

        return ResponseDto.success(responses);
    }
}
