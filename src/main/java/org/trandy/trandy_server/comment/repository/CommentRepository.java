package org.trandy.trandy_server.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trandy.trandy_server.comment.domain.VoteComment;

@Repository
public interface CommentRepository extends JpaRepository<VoteComment, Long> {
}
