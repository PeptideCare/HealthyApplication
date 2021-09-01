package com.healthyapplication.healthyapplication.apicontroller;

import com.healthyapplication.healthyapplication.domain.Image;
import com.healthyapplication.healthyapplication.domain.Member;
import com.healthyapplication.healthyapplication.domain.Title;
import com.healthyapplication.healthyapplication.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/api/member/insert")
    public String join(@RequestBody Member member) {
        return memberService.join(member);
    }

    // 로그인
    @PostMapping("/api/member/login")
    public String login(@RequestBody Member member) {
        Optional<Member> findMember = memberService.findById(member.getId());
        if (findMember.isEmpty()) {
            // 없는 회원
            return "0";
        } else {
            // 로그인 성공
            if (findMember.get().getPw().equals(member.getPw())) {
                return findMember.get().getId();
                // 로그인 실패
            } else {
                return "0";
            }
        }
    }

    // 모든 회원 조회
    @GetMapping("/api/member/find")
    public Result find() {
        List<Member> members = memberService.findAll();
        List<MemberDto> list = new ArrayList<>();

        for (Member member : members) {
            List<TitleDto> titleDtos = new ArrayList<>();
            List<Title> title = member.getTitle();
            for (Title title1 : title) {
                TitleDto titleDto = new TitleDto(title1.getId(), title1.getName());
                titleDtos.add(titleDto);
            }

            Image image = member.getImage();
            ImageDto imageDto;
            if (image == null) {
                imageDto = new ImageDto(Long.valueOf(0), "0");
            } else {
                imageDto = new ImageDto(image.getId(), image.getName());
            }
            MemberDto memberDto = new MemberDto(member.getId(), member.getPw(),
                    member.getNickname(), member.getHeight(), member.getWeight(),
                    member.getSex(), imageDto, titleDtos);
            list.add(memberDto);
        }

        return new Result(list);
    }

    // 아이디로 회원 조회
    @GetMapping("/api/member/{id}/find")
    public MemberDto findOne(@PathVariable String id) {
        Member member = memberService.findById(id).get();
        List<TitleDto> titleDtos = new ArrayList<>();

        List<Title> title = member.getTitle();
        for (Title title1 : title) {
            TitleDto titleDto = new TitleDto(title1.getId(), title1.getName());
            titleDtos.add(titleDto);
        }

        Image image = member.getImage();
        ImageDto imageDto;
        if (image == null) {
            imageDto = new ImageDto(Long.valueOf(0), "0");
        } else {
            imageDto = new ImageDto(image.getId(), image.getName());
        }

        MemberDto memberDto = new MemberDto(member.getId(), member.getPw(),
                member.getNickname(), member.getHeight(), member.getWeight(),
                member.getSex(), imageDto, titleDtos);
        return memberDto;
    }

    // json 오브젝트 객체
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private List<MemberDto> member;
    }

    //회원 DTO
    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String id;
        private String pw;
        private String nickname;
        private double height;
        private double weight;
        private String sex;
        private ImageDto image;
        private List<TitleDto> title;
    }

    // 칭호 dto
    @Data
    @AllArgsConstructor
    static class TitleDto{
        private Long id;
        private String name;
    }

    // 이미지 DTO
    @Data
    @AllArgsConstructor
    static class ImageDto {
        private Long id;
        private String name;
    }

}
