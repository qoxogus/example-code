package com.cache.project.service;

import com.cache.project.member.Member;
import com.cache.project.member.MemberDto;
import com.cache.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member joinMember(MemberDto memberDto) {
        return memberRepository.save(memberDto.toEntity());
    }

    public Member findMemberNoCache(String username) {
        slowQuery(2000);
        return memberRepository.findMemberByUsername(username);
    }

    @Cacheable(value="findMemberCache", key = "#username") // 해당 캐시 사용
    public Member findMemberCache(String username) {
        slowQuery(2000);
        return memberRepository.findMemberByUsername(username);
    }

    @CacheEvict(value = "findMemberCache", key="#username") //해당 캐시 삭제
    public String refresh(String username) {
        return username + "님의 Cache Clear !";
    }

    // 빅쿼리를 돌린다는 가정
    private void slowQuery(long seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
