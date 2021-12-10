package com.cache.project.controller;

import com.cache.project.member.Member;
import com.cache.project.member.MemberDto;
import com.cache.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public Member joinMember(@RequestBody MemberDto memberDto) {
        return memberService.joinMember(memberDto);
    }

    @GetMapping("/nocache/{username}")
    public Member getNocacheMember(@PathVariable String username) {
        long start = System.currentTimeMillis(); // 수행시간 측정
        Member member = memberService.findMemberNoCache(username);
        long end = System.currentTimeMillis();

        log.info(username+ "의 NoCache 수행시간 : "+ (end - start)); // 수행시간 logging

        return member;
    }

    @GetMapping("/cache/{username}")
    public Member getCacheMember(@PathVariable String username) {
        long start = System.currentTimeMillis(); // 수행시간 측정
        Member member = memberService.findMemberCache(username);
        long end = System.currentTimeMillis();

        log.info(username+ "의 Cache 수행시간 : "+ (end - start)); // 수행시간 logging

        return member;
    }

    @GetMapping("/cache/refresh/{username}")
    public String refresh(@PathVariable String username){
        memberService.refresh(username); // 캐시제거
        log.info(username+ "의 Cache Clear!");
        return username + " cache clear!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Ehcache !";
    }
}
