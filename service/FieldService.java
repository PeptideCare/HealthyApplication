package com.healthyapplication.healthyapplication.service;

import com.healthyapplication.healthyapplication.domain.Field;
import com.healthyapplication.healthyapplication.domain.Member;
import com.healthyapplication.healthyapplication.repository.FieldRepository;
import com.healthyapplication.healthyapplication.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FieldService {

    private final FieldRepository fieldRepository;
    private final MemberRepository memberRepository;

    //저장
    @Transactional
    public void save(Field field, String id) {
        Field findField = fieldRepository.findByFieldName(id, field.getName());

        if (findField != null){
            findField.addTime(field.getHour());
        } else {
            Optional<Member> findMember = memberRepository.findById(id);
            if (findMember.isEmpty()) return;
            field.setMember(findMember.get());
            fieldRepository.save(field);
        }
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

    //멤버 아이디로 조회
    public List<Field> findAllById(String id) {
        List<Field> fields = fieldRepository.findAllById(id);
        return fields;
    }
}
