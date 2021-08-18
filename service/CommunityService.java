package com.healthyapplication.healthyapplication.service;


import com.healthyapplication.healthyapplication.domain.Community;
import com.healthyapplication.healthyapplication.domain.Image;
import com.healthyapplication.healthyapplication.domain.Member;
import com.healthyapplication.healthyapplication.repository.CommunityRepository;
import com.healthyapplication.healthyapplication.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;

    //저장
    @Transactional
    public void save(Community community, String member_id) {
        Member member = memberRepository.findById(member_id).get();
        community.setMember(member);
        communityRepository.save(community);
    }

    //삭제
    @Transactional
    public void delete(Community community) {
        communityRepository.delete(community);
    }

    //조회
    public Community findOne(Long id) {
        Community community = communityRepository.findById(id).get();
        return community;
    }

    //모든 조회
    public List<Community> findAll() {
        List<Community> communities = communityRepository.findAll();
        return communities;
    }

    //변경 감지 수정
    public void update(Community community) {
        Community findCom = communityRepository.findById(community.getId()).get();
        findCom.update(community);
    }
}
