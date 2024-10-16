package org.trandy.trandy_server.post.repository.QueryDSL.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.trandy.trandy_server.comment.domain.QVoteComment;
import org.trandy.trandy_server.post.domain.Post;
import org.trandy.trandy_server.post.domain.QPost;
import org.trandy.trandy_server.post.domain.VoteResult;
import org.trandy.trandy_server.post.domain.dto.response.TrendingPostResponse;
import org.trandy.trandy_server.post.repository.QueryDSL.QueryPostRepository;
import org.trandy.trandy_server.report.domain.QReport;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryPostRepositoryImpl implements QueryPostRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QPost post = QPost.post;
    private final QVoteComment voteComment = QVoteComment.voteComment;
    private final QReport report = QReport.report;

    @Override
    public List<TrendingPostResponse> retrieveTrendingPostList() {
        // Projection 으로 DTO 직접 조회하는 방식으로 구현한다.
        List<TrendingPostResponse> posts = jpaQueryFactory
                .select(Projections.constructor(
                        TrendingPostResponse.class,
                        post.id,
                        post.title,
                        post.contents,
                        post.voteStatus,
                        post.voteResult,
                        post.hashtag,
                        post.deleted,
                        post.deletedAt,
                        post.member,
                        post.category,
                        post.image,
                        post.reports,
                        post.voteComments
                ))
                .from(post)
                .leftJoin(post.voteComments, voteComment).fetchJoin().distinct()
                .leftJoin(post.reports, report).fetchJoin().distinct()
                .where(post.voteResult.eq(VoteResult.TRENDING),
                        post.deleted.isFalse())
                .fetch();

        return posts;
    }
}
