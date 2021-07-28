package com.healthyapplication.healthyapplication.service;

import com.healthyapplication.healthyapplication.domain.Image;
import com.healthyapplication.healthyapplication.domain.Member;
import com.healthyapplication.healthyapplication.domain.Title;
import com.healthyapplication.healthyapplication.repository.MemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    //저장
    @Transactional
    public void join(Member member) {
        memberRepository.save(member);
    }

    //모든 회원 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    //회원 조회
    public MemberOne findOne(String id) {
        Member member = memberRepository.findOne(id);
        MemberOne memberOne = new MemberOne();
        memberOne.setId(member.getId());
        memberOne.setPw(member.getPw());
        memberOne.setNickname(member.getNickname());
        memberOne.setImage(member.getImage());

        return memberOne;
    }

    //회원 조회 DTO
    @Data
    static class MemberOne {
        private String id;
        private String pw;
        private String nickname;
        private Image image;
    }

}
