package com.healthyapplication.healthyapplication.service;

import com.healthyapplication.healthyapplication.domain.Image;
import com.healthyapplication.healthyapplication.domain.Member;
import com.healthyapplication.healthyapplication.repository.MemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    //저장
    @Transactional
    public String join(Member member) {
        return validateMember(member);
    }

    //중복 회원 방지 메서드
    public String validateMember(Member member) {
        Optional<Member> findMember = findById(member.getId());
        if (findMember.isPresent()) {
            return "0";
        }
        else {
            Member savedMember = memberRepository.save(member);
            return savedMember.getId();
        }
    }

    //모든 회원 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    //회원 조회(페치조인)
    public Member findOne(String id) {
        Member member = memberRepository.findOne(id);
        return member;
    }

    //회원 조회
    public Optional<Member> findById(String id) {
        Optional<Member> member = memberRepository.findById(id);
        return member;
    }

    //회원 탈퇴
    @Transactional
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    //회원 업데이트
    public void update(Member member) {
        Member findMember = memberRepository.findById(member.getId()).get();
        findMember.updateMember(member);
    }
}
