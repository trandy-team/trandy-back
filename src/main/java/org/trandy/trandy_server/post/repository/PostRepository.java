package org.trandy.trandy_server.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trandy.trandy_server.post.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
