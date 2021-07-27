package com.healthyapplication.healthyapplication.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Title {

    @Id
    @GeneratedValue
    @Column(name = "title_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
