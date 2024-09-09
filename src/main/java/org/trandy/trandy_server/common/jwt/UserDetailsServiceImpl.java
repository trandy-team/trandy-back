package org.trandy.trandy_server.common.jwt;

import org.trandy.trandy_server.exception.CustomException;
import org.trandy.trandy_server.exception.ExceptionStatus;
import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Spring 에서 제시한 UserDetailsService의 구현체

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberIdAndDeletedFalse(memberId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.MemberNotFoundException));

        return new UserDetailsImpl(member);
    }
}
