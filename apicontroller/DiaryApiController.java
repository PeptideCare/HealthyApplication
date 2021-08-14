package com.healthyapplication.healthyapplication.apicontroller;

import com.healthyapplication.healthyapplication.domain.Diary;
import com.healthyapplication.healthyapplication.service.DiaryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class DiaryApiController {

    private final DiaryService diaryService;

    // 작성
    @PostMapping("/api/diary/{member_id}/write")
    public void write(@RequestBody Diary diary, @PathVariable String member_id) {
        diaryService.save(diary, member_id);
    }

    // 조회
    @GetMapping("/api/diary/{id}/find")
    public DiaryDto find(@PathVariable Long id) {
        Diary diary = diaryService.findOne(id);
        DiaryDto diaryDto = new DiaryDto(diary.getId(), diary.getDate(), diary.getField(), diary.getContent(), diary.getHour());
        return diaryDto;
    }

    // 조회 DTO
    @Data
    @AllArgsConstructor
    static class DiaryDto {
        private Long id;
        private LocalDateTime date;
        private String field;
        private String content;
        private int hour;
    }
}
