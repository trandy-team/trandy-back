package org.trandy.trandy_server.post.repository.QueryDSL;


import org.trandy.trandy_server.post.domain.dto.response.PostByCategoryResponse;
import org.trandy.trandy_server.post.domain.dto.response.PostByMemberIdResponse;
import org.trandy.trandy_server.post.domain.dto.response.TrendingPostResponse;

import java.util.List;

public interface QueryPostRepository {

    List<TrendingPostResponse> retrieveTrendingPostList();

    List<PostByMemberIdResponse> retrievePostListByMemberId(long memberId);
}
