package org.trandy.trandy_server.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.trandy.trandy_server.post.domain.Post;
import org.trandy.trandy_server.post.domain.dto.response.PostByCategoryResponse;
import org.trandy.trandy_server.post.repository.QueryDSL.QueryPostRepository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, QueryPostRepository {
    @Query(value =
            "SELECT " +
            "   p.id AS postId," +
            "   p.vote_count AS voteCount," +
            "   m.nickname AS nickname, " +
            "   CASE " +
            "       WHEN c.member_id IS NOT NULL " +
            "       THEN true ELSE false " +
            "   END AS has_voted, " +
            "   DATE_FORMAT(TIMEDIFF(DATE_ADD(p.created_at, INTERVAL 3 DAY), NOW()), '%H:%i:%s') AS vote_deadline, " +
            "   DATE_FORMAT(p.created_at, '%Y.%m.%d') AS created_at " +
            "FROM post p " +
            "   LEFT JOIN vote_comment c " +
                    "ON p.id = c.post_id " +
            "   LEFT JOIN member m " +
                    "ON p.member_id = m.id " +
                    "AND c.member_id = :memberId " +
            "WHERE p.category_id = :categoryId", nativeQuery = true)
    List<Object[]> retrieveVoteListByCategory(long categoryId, int memberId);
}
