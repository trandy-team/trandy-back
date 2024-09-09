package org.trandy.trandy_server.member.repository.queryDSL;

import org.trandy.trandy_server.member.domain.Member;

import java.util.List;

// Query Method 가 아닌 일반 쿼리문을 작성할 때 사용한다.

public interface QueryMemberRepository {
    List<Member> queryFindAllMember();
}
