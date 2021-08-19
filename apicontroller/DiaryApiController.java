package com.healthyapplication.healthyapplication.apicontroller;

import com.healthyapplication.healthyapplication.domain.Diary;
import com.healthyapplication.healthyapplication.service.DiaryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryApiController {

    private final DiaryService diaryService;

    // 작성
    @PostMapping("/api/diary/{member_id}/insert")
    public void write(@RequestBody Diary diary, @PathVariable String member_id) {
        diary.setDate(LocalDate.now());
        diaryService.save(diary, member_id);
    }

    // 조회
    @GetMapping("/api/diary/{member_id}/{date}/find")
    public Result find(@PathVariable String member_id, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<Diary> diaryList = diaryService.findByDate(member_id, date);
        List<DiaryDto> diaryDtos = new ArrayList<>();

        for (Diary diary : diaryList) {
            DiaryDto diaryDto = new DiaryDto(diary.getId(), diary.getDate(), diary.getField(), diary.getContent(), diary.getHour());
            diaryDtos.add(diaryDto);
        }

        return new Result(diaryDtos);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        List<DiaryDto> diary;
    }

    // 조회 DTO
    @Data
    @AllArgsConstructor
    static class DiaryDto {
        private Long id;
        private LocalDate date;
        private String field;
        private String content;
        private int hour;
    }
}
