package com.healthyapplication.healthyapplication.apicontroller;

import com.healthyapplication.healthyapplication.domain.Title;
import com.healthyapplication.healthyapplication.service.TitleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TitleApiController {

    private final TitleService titleService;

    // 저장
    @PostMapping("/api/title/{member_id}/insert")
    public void save(@RequestBody Title title, @PathVariable String member_id){
        titleService.save(title, member_id);
    }

    // 멤버별 모든 칭호 조회
    @GetMapping("/api/title/{member_id}/find")
    public Result findAllById(@PathVariable String member_id) {
        List<Title> titles = titleService.findAllById(member_id);
        ArrayList<TitleDto> list = new ArrayList<>();

        for (Title title : titles) {
            TitleDto dto = new TitleDto(title.getId(), title.getName());
            list.add(dto);
        }
        return new Result(list);
    }

    // Json 객체
    @Data
    @AllArgsConstructor
    static class TitleDto {
        private Long id;
        private String name;
    }

    // Title Dto
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private List<TitleDto> list;
    }

}
