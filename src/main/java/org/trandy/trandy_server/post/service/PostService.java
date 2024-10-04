package org.trandy.trandy_server.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trandy.trandy_server.category.domain.Category;
import org.trandy.trandy_server.category.service.CategoryService;
import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.ResponseDto;
import org.trandy.trandy_server.image.domain.Image;
import org.trandy.trandy_server.image.service.ImageService;
import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.member.service.MemberService;
import org.trandy.trandy_server.post.domain.Post;
import org.trandy.trandy_server.post.domain.VoteStatus;
import org.trandy.trandy_server.post.domain.dto.request.EnrollVoteRequest;
import org.trandy.trandy_server.post.repository.PostRepository;
import org.trandy.trandy_server.util.S3Util;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final S3Util s3Util;

    @Transactional
    public ResponseDto enrollVote(EnrollVoteRequest request, long memberId) {
        // 멤버 객체 영속화 (개발용)
        Member member = memberService.retrieveMemberMockData(memberId);

        // Category Persistence
        Category category = categoryService.retrieveCategory(request.getCategoryId());

        // Trim Hashtag String
        String hashtag = request.getHashtag().trim();

        // Post 객체 기 생성
        Post post = postRepository.save(Post.builder()
                .title(request.getTitle())
                .contents(request.getContents())
                .hashtag(hashtag)
                .voteStatus(VoteStatus.IN_PROGRESS)
                .category(category)
                .member(member)
                .deleted(Constants.DELETED_NOT)
                .build());

        // S3 File Upload
        String imageUrl = s3Util.uploadFile(request.getImage());
        Image image = imageService.saveImage(imageUrl, post);

        // Post 객체 image 초기화
        post.toBuilder()
                .image(image);

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }
}
