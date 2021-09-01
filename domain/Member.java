package com.healthyapplication.healthyapplication.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@RequiredArgsConstructor
public class Member {

    public Member(String id, String pw, String nickname, double height, double weight, String sex) {
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
    }

    @Id
    @Column(name = "member_id")
    private String id;

    @Column
    private String pw;
    private String nickname;
    private double height;
    private double weight;
    private String sex;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "member")
    private List<Title> title;

    @OneToMany(mappedBy = "member")
    private List<Field> field;

    @OneToMany(mappedBy = "member")
    private List<Diary> diary;

    @OneToMany(mappedBy = "member")
    private List<Community> community;

    // 변경감지 업데이트
    public void updateMember(Member member) {
        this.pw = member.getPw();
        this.nickname = member.getNickname();
        this.height = member.getHeight();
        this.weight = member.getWeight();
        this.sex = member.getSex();
    }

    // 이미지 변경
    public void updateImage(Image image) {
        this.image = image;
    }
}
