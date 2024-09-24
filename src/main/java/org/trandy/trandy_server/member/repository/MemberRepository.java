package org.trandy.trandy_server.member.repository;

import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.member.repository.queryDSL.QueryMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Query Method 사용 시 등록한다.
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, QueryMemberRepository {
    Optional<Member> findByEmailAndDeletedFalse(String memberId);
    Optional<Member> findByEmail(String memberId);
}
