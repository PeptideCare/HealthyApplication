package com.healthyapplication.healthyapplication.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

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

}
