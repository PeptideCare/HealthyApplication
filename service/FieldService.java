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
            checkField(id, findField, findField.getHour()+field.getHour());

        } else {
            Optional<Member> findMember = memberRepository.findById(id);
            if (findMember.isEmpty()) return;
            field.setMember(findMember.get());

            fieldRepository.save(field);
            checkField(id, field, field.getHour());
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

    // 필드 시간 별 이미지 칭호 저장
    private void checkField(String id, Field findField, int hour) {
        if (findField.getName().equals("공부")) {
            if (hour >= 10000) {
                Image image = new Image();
                image.setName("Trillionaire");
                imageService.update(image, id);

                Title title = new Title();
                title.setName("공부의 神");
                titleService.save(title, id);

            } else if (hour >= 1000) {
                Image image = new Image();
                image.setName("Billionaire");
                imageService.update(image, id);

                Title title = new Title();
                title.setName("공부는 나의길");
                titleService.save(title, id);
            } else if (hour >= 100) {
                Image image = new Image();
                image.setName("Millionaire");
                imageService.update(image, id);

                Title title = new Title();
                title.setName("공린이");
                titleService.save(title, id);
            }
        }

        if (findField.getName().equals("운동")) {
            if (hour >= 10000) {
                Image image = new Image();
                image.setName("보디빌더");
                imageService.update(image, id);

                Title title = new Title();
                title.setName("운동의 神");
                titleService.save(title, id);
            } else if (hour >= 1000) {
                Image image = new Image();
                image.setName("트레이너");
                imageService.update(image, id);

                Title title = new Title();
                title.setName("운동은 나의길");
                titleService.save(title, id);
            } else if (hour >= 100) {
                Image image = new Image();
                image.setName("모델");
                imageService.update(image, id);

                Title title = new Title();
                title.setName("헬린이");
                titleService.save(title, id);
            }
        }
    }
}
