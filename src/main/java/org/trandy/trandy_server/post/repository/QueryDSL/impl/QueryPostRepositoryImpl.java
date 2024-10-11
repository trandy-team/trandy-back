package org.trandy.trandy_server.post.repository.QueryDSL.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.trandy.trandy_server.post.domain.Post;
import org.trandy.trandy_server.post.domain.dto.response.TrendingPostResponse;
import org.trandy.trandy_server.post.repository.QueryDSL.QueryPostRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryPostRepositoryImpl implements QueryPostRepository {
    @Override
    public List<Post> retrieveTrendingPostList() {
        return null;
//        return List< TrendingPostResponse > trendingPosts = jpaQueryFactory
//                .select(projection(TrendingPostDTO.class, post.id, post.title, post.content))
//                .from(post)
//                .where(
//                        post.voteResult.eq("TRENDING"),
//                        post.deleted.isFalse()
//                )
//                .fetch();
    }
}
