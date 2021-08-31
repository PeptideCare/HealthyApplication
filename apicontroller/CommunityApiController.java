package com.healthyapplication.healthyapplication.apicontroller;

import com.healthyapplication.healthyapplication.domain.Community;
import com.healthyapplication.healthyapplication.domain.Image;
import com.healthyapplication.healthyapplication.domain.Title;
import com.healthyapplication.healthyapplication.service.CommunityService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommunityApiController {

    private final CommunityService communityService;

    //글쓰기
    @PostMapping("/api/community/{member_id}/insert")
    public void insert (@RequestBody Community community, @PathVariable String member_id) {
        community.setDate(LocalDateTime.now());
        communityService.save(community, member_id);
    }

    //모든 커뮤니티 조회
    @GetMapping("/api/community/find")
    public Result find() {
        List<Community> all = communityService.findAll();
        ArrayList<ComDto> list = new ArrayList<>();

        for (Community community : all) {

            ArrayList<TitleDto> titleDtos = new ArrayList<>();
            List<Title> title = community.getMember().getTitle();

            for (Title title1 : title) {
                TitleDto titleDto = new TitleDto(title1.getId(), title1.getName());
                titleDtos.add(titleDto);
            }

            ImageDto imageDto = new ImageDto(community.getMember().getImage().getId(), community.getMember().getImage().getName());

            MemberDto memberDto = new MemberDto(community.getMember().getNickname(), imageDto, titleDtos);
            ComDto comDto = new ComDto(community.getId(), community.getTitle(), community.getContent(), community.getDate(), memberDto);

            list.add(comDto);
        }
        return new Result(list);
    }

    // 하나의 커뮤니티 조회
    @GetMapping("/api/community/{id}/find")
    public ComDto findOne(@PathVariable Long id) {
        Community community = communityService.findOne(id);
        List<TitleDto> titleDtos = new ArrayList<>();

        List<Title> title = community.getMember().getTitle();
        for (Title title1 : title) {
            TitleDto titleDto = new TitleDto(title1.getId(), title1.getName());
            titleDtos.add(titleDto);
        }

        ImageDto imageDto = new ImageDto(community.getMember().getImage().getId(), community.getMember().getImage().getName());

        MemberDto memberDto = new MemberDto(community.getMember().getNickname(), imageDto, titleDtos);
        ComDto comDto = new ComDto(community.getId(), community.getTitle(), community.getContent(), community.getDate(), memberDto);

        return comDto;
    }

    // 커뮤니티 dto
    @Data
    @AllArgsConstructor
    static class ComDto {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime time;
        private MemberDto member;
    }

    // 멤버 dto
    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String nickname;
        private ImageDto image;
        private List<TitleDto> titles;
    }

    // 이미지 dto
    @Data
    @AllArgsConstructor
    static class ImageDto {
        private Long id;
        private String name;
    }

    // 칭호 dto
    @Data
    @AllArgsConstructor
    static class TitleDto{
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private List<ComDto> community;
    }
}
