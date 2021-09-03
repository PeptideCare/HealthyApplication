package com.healthyapplication.healthyapplication.service;

import com.healthyapplication.healthyapplication.domain.Field;
import com.healthyapplication.healthyapplication.domain.Image;
import com.healthyapplication.healthyapplication.domain.Member;
import com.healthyapplication.healthyapplication.domain.Title;
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
    private final TitleService titleService;
    private final ImageService imageService;

    //저장
    @Transactional
    public void save(Field field, String id) {
        Field findField = fieldRepository.findByFieldName(id, field.getName());

        // 칭호 이벤트 처리
        if (findField != null){
            findField.addTime(field.getHour());
            if (findField.getName().equals("공부")) {
                if (findField.getHour() >= 10000) {
                    Image image = new Image();
                    image.setName("대통령");
                    imageService.update(image, id);

                    Title title = new Title();
                    title.setName("공부의 신");
                    titleService.save(title, id);

                } else if (findField.getHour() >= 1000) {
                    Image image = new Image();
                    image.setName("국회의원");
                    imageService.update(image, id);

                    Title title = new Title();
                    title.setName("공부는 나의길");
                    titleService.save(title, id);
                } else if (findField.getHour() >= 100) {
                    Image image = new Image();
                    image.setName("공무원");
                    imageService.update(image, id);

                    Title title = new Title();
                    title.setName("공부 꿈나무");
                    titleService.save(title, id);
                }
            }

            if (findField.getName().equals("운동")) {
                if (findField.getHour() >= 10000) {
                    Image image = new Image();
                    image.setName("보디빌더");
                    imageService.update(image, id);

                    Title title = new Title();
                    title.setName("운동의 신");
                    titleService.save(title, id);
                } else if (findField.getHour() >= 1000) {
                    Image image = new Image();
                    image.setName("스포츠 모델");
                    imageService.update(image, id);

                    Title title = new Title();
                    title.setName("운동은 나의길");
                    titleService.save(title, id);
                } else if (findField.getHour() >= 100) {
                    Image image = new Image();
                    image.setName("몸짱");
                    imageService.update(image, id);

                    Title title = new Title();
                    title.setName("운동 꿈나무");
                    titleService.save(title, id);
                }
            }

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
