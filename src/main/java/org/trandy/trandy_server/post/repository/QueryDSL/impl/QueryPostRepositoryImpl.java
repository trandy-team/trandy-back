package org.trandy.trandy_server.post.repository.QueryDSL.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.trandy.trandy_server.comment.domain.QVoteComment;
import org.trandy.trandy_server.member.domain.QMember;
import org.trandy.trandy_server.post.domain.Post;
import org.trandy.trandy_server.post.domain.QPost;
import org.trandy.trandy_server.post.domain.VoteResult;
import org.trandy.trandy_server.post.domain.dto.response.PostByCategoryResponse;
import org.trandy.trandy_server.post.domain.dto.response.TrendingPostResponse;
import org.trandy.trandy_server.post.repository.QueryDSL.QueryPostRepository;
import org.trandy.trandy_server.report.domain.QReport;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class QueryPostRepositoryImpl implements QueryPostRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QPost post = QPost.post;
    private final QVoteComment voteComment = QVoteComment.voteComment;
    private final QMember member = QMember.member;

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
                        post.member.nickname,
                        post.category.categoryName,
                        post.image.imageUrl
                ))
                .from(post)
                .where(post.voteResult.eq(VoteResult.TRENDING),
                        post.deleted.isFalse())
                .fetch();

        return posts;
    }

    @Override
    public List<PostByCategoryResponse> retrieveVoteListByCategory(long categoryId, long memberId) {
        List<PostByCategoryResponse> posts = jpaQueryFactory
                .select(Projections.constructor(PostByCategoryResponse.class,
                        post.id,
                        post.voteCount,
                        member.nickname,
                        new CaseBuilder()
                                .when(voteComment.member.id.isNotNull())
                                .then(true)
                                .otherwise(false).as("hasVoted"),
                        Expressions.stringTemplate(
                                "DATE_FORMAT(TIMEDIFF(DATE_ADD({0}, INTERVAL 3 DAY), NOW()), '%H:%i:%s')",
                                post.createdAt
                        ).as("voteDeadline"),
                        Expressions.stringTemplate(
                                "DATE_FORMAT({0}, '%Y.%m.%d')", post.createdAt
                        ).as("createdAt")
                ))
                .from(post)
                .leftJoin(voteComment)
                .on(post.id.eq(voteComment.post.id))
                .leftJoin(member)
                .on(post.member.id.eq(member.id)
                        .and(voteComment.member.id.eq(memberId)))
                .fetch();

        return posts;
    }
}
