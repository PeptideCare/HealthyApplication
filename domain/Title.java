package com.healthyapplication.healthyapplication.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Title {

    public Title(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "title_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
