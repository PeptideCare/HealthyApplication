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
    public void join(Member member) {
//        validateMember(member);
        memberRepository.save(member);
    }

    //중복 회원 방지 메서드
    public void validateMember(Member member) {
        Member findMember = findById(member.getId()).get();
        if (findMember.getId() != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
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
