package org.trandy.trandy_server.post.repository.QueryDSL.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.trandy.trandy_server.post.domain.Post;
import org.trandy.trandy_server.post.domain.QPost;
import org.trandy.trandy_server.post.domain.VoteResult;
import org.trandy.trandy_server.post.domain.dto.response.TrendingPostResponse;
import org.trandy.trandy_server.post.repository.QueryDSL.QueryPostRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryPostRepositoryImpl implements QueryPostRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QPost post = QPost.post;

    @Override
    public List<Post> retrieveTrendingPostList() {
        List<Post> posts = jpaQueryFactory.selectFrom(post)
                .where(post.voteResult.eq(VoteResult.TRENDING),
                        post.deleted.isFalse()
                )
                .fetch();

        return posts;
    }
}
