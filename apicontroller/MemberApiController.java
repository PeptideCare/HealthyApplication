package com.healthyapplication.healthyapplication.apicontroller;

import com.healthyapplication.healthyapplication.domain.Image;
import com.healthyapplication.healthyapplication.domain.Member;
import com.healthyapplication.healthyapplication.domain.Title;
import com.healthyapplication.healthyapplication.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/api/member/new")
    public void join(@RequestBody Member member) {
        memberService.join(member);
    }

    // 로그인
    @PostMapping("/api/member/login")
    public String login(@RequestBody Member member) {
        Member findMember = memberService.findById(member.getId());
        if (findMember == null) {
            return "0";
        } else {
            return findMember.getId();
        }
    }

    // 모든 회원 조회
    @GetMapping("/api/member/find")
    public Result find() {
        List<Member> members = memberService.findAll();
        List<MemberDto> list = new ArrayList<>();

        for (Member member : members) {
            MemberDto memberDto = new MemberDto(member.getId(), member.getPw(),
                    member.getNickname(), member.getHeight(), member.getWeight(),
                    member.getSex(), member.getImage(), member.getTitle());
            list.add(memberDto);
        }

        return new Result(list);
    }

    // 아이디로 회원 조회
    @GetMapping("/api/member/{id}/find")
    public MemberDto findOne(@PathVariable String id) {
        Member member = memberService.findOne(id);
        MemberDto memberDto = new MemberDto(member.getId(), member.getPw(),
                member.getNickname(), member.getHeight(), member.getWeight(),
                member.getSex(), member.getImage(), member.getTitle());
        return memberDto;
    }

    // json 오브젝트 객체
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private List<MemberDto> memberDto;
    }

    //회원 DTO
    @Data
    static class MemberDto {
        private String id;
        private String pw;
        private String nickname;
        private double height;
        private double weight;
        private String sex;
        private Image image;
        private List<Title> title;

        public MemberDto(String id, String pw, String nickname, double height, double weight, String sex, Image image, List<Title> title) {
            this.id = id;
            this.pw = pw;
            this.nickname = nickname;
            this.height = height;
            this.weight = weight;
            this.sex = sex;
            this.image = image;
            this.title = title;
        }
    }

}
