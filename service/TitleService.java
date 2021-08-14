package com.healthyapplication.healthyapplication.service;

import com.healthyapplication.healthyapplication.domain.Member;
import com.healthyapplication.healthyapplication.domain.Title;
import com.healthyapplication.healthyapplication.repository.MemberRepository;
import com.healthyapplication.healthyapplication.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TitleService {

    private final TitleRepository titleRepository;
    private final MemberRepository memberRepository;

    //저장
    @Transactional
    public void save(Title title, String id) {
        Member member = memberRepository.findById(id).get();
        title.setMember(member);
        titleRepository.save(title);
    }

    //삭제
    @Transactional
    public void delete(Title title) {
        titleRepository.delete(title);
    }

    //조회
    public Title findOne(Long id) {
        Title title = titleRepository.findById(id).get();
        return title;
    }

    //멤버별 모든 조회
    public List<Title> findAllById(String id) {
        List<Title> titles = titleRepository.findAllById(id);
        return titles;
    }

}
