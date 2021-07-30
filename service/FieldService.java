package com.healthyapplication.healthyapplication.service;

import com.healthyapplication.healthyapplication.domain.Field;
import com.healthyapplication.healthyapplication.repository.FieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FieldService {

    private final FieldRepository fieldRepository;

    //저장
    @Transactional
    public void save(Field field) {
        fieldRepository.save(field);
    }

    //삭제
    @Transactional
    public void delete(Field field) {
        fieldRepository.delete(field);
    }

    //조회
    public Field findOne(Long id) {
        Field field = fieldRepository.findById(id).get();
        return field;
    }

}
