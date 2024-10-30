package org.trandy.trandy_server.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trandy.trandy_server.comment.domain.VoteComment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<VoteComment, Long> {
    Optional<VoteComment> findByPostIdAndMemberId(long postId, long memberId);

    List<VoteComment> findByPostIdAndContentsIsNotNullAndContentsNot(long postId, String emptyString);

    List<VoteComment> findByMemberId(long memberId);
}
