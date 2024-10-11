package org.trandy.trandy_server.post.repository.QueryDSL;

import org.trandy.trandy_server.post.domain.Post;

import java.util.List;

public interface QueryPostRepository {

    List<Post> retrieveTrendingPostList();
}
