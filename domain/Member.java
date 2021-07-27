package com.healthyapplication.healthyapplication.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @Column(name = "member_id")
    private String id;

    private String pw;
    private String nickname;
    private double height;
    private double weight;
    private String sex;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "title")
    private List<Title> title;

    @OneToMany(mappedBy = "field")
    private List<Field> field;

    @OneToMany(mappedBy = "diary")
    private List<Diary> diary;

    @OneToMany(mappedBy = "community")
    private List<Community> community;

}
