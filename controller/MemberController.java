package com.healthyapplication.healthyapplication.controller;

import com.healthyapplication.healthyapplication.domain.Image;
import com.healthyapplication.healthyapplication.domain.Member;
import com.healthyapplication.healthyapplication.domain.Title;
import com.healthyapplication.healthyapplication.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/v1/join")
    public void join(@RequestBody  MemberDto memberDto) {

        Member member = new Member();
        member.setId(memberDto.getId());
        member.setPw(memberDto.getPw());
        member.setNickname(memberDto.getNickname());
        member.setHeight(memberDto.getHeight());
        member.setWeight(memberDto.getWeight());
        member.setSex(memberDto.getSex());

        memberService.join(member);
    }

    // 로그인


    //회원 DTO
    @Data
    static class MemberDto {
        private String id;
        private String pw;
        private String nickname;
        private double height;
        private double weight;
        private String sex;
    }

}
