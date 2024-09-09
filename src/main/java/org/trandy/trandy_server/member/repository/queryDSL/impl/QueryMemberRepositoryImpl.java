package org.trandy.trandy_server.member.repository.queryDSL.impl;

import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.member.domain.QMember;
import org.trandy.trandy_server.member.repository.queryDSL.QueryMemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryMemberRepositoryImpl implements QueryMemberRepository {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Member> queryFindAllMember() {
    QMember member = QMember.member;
        return jpaQueryFactory
                .select(member)
                .from(member)
                .fetch();
    }
}
