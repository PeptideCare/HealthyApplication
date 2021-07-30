package com.healthyapplication.healthyapplication.service;

import com.healthyapplication.healthyapplication.domain.Diary;
import com.healthyapplication.healthyapplication.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    //저장
    @Transactional
    public void save(Diary diary) {
        diaryRepository.save(diary);
    }

    //삭제
    @Transactional
    public void delete(Diary diary) {
        diaryRepository.delete(diary);
    }

    //조회
    public Diary findOne(Long id) {
        Diary diary = diaryRepository.findById(id).get();
        return diary;
    }

    //멤버별 모든 조회
    public List<Diary> findAllById(String id) {
        List<Diary> diaries = diaryRepository.findAllById(id);
        return diaries;
    }


}
