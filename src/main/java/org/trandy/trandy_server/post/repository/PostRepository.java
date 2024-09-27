package org.trandy.trandy_server.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trandy.trandy_server.post.domain.Post;
import org.trandy.trandy_server.post.repository.QueryDSL.QueryPostRepository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, QueryPostRepository {
}
