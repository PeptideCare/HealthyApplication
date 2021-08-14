package com.healthyapplication.healthyapplication.apicontroller;

import com.healthyapplication.healthyapplication.domain.Field;
import com.healthyapplication.healthyapplication.domain.Member;
import com.healthyapplication.healthyapplication.service.FieldService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FieldApiController {

    private final FieldService fieldService;

    // 저장(회원)
    @PostMapping("/api/field/{member_id}/insert")
    public void insert(@RequestBody Field field, @PathVariable String member_id) {
        fieldService.save(field, member_id);
    }

    // 멤버 아이디로 모든 필드 조회
    @GetMapping("/api/field/{member_id}/find")
    public Result findById(@PathVariable String member_id) {
        List<Field> fields = fieldService.findAllById(member_id);
        List<FieldDto> list = new ArrayList<>();

        for (Field field : fields) {
            FieldDto fieldDto = new FieldDto(field.getId(), field.getName(), field.getHour());
            list.add(fieldDto);
        }
        return new Result(list);
    }

    // 하나의 필드 조회
    @GetMapping("/api/field/{id}/find")
    public FieldDto find(@PathVariable Long id) {
        Field field = fieldService.findOne(id);
        FieldDto fieldDto = new FieldDto(field.getId(), field.getName(), field.getHour());
        return fieldDto;
    }

    // Json 오브젝트 객체
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private List<FieldDto> fieldDtos;
    }

    // 필드 DTO
    @Data
    @AllArgsConstructor
    static class FieldDto {
        private Long id;
        private String name;
        private int hour;
    }
}
