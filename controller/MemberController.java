package com.healthyapplication.healthyapplication.controller;

import com.healthyapplication.healthyapplication.domain.Member;
import com.healthyapplication.healthyapplication.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

//    //회원가입
//    @GetMapping("/member/new")
//    public MemberDto join(Member member) {
//
//    }
//
//    // 로그인



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
