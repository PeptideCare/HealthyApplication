package com.healthyapplication.healthyapplication.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Image {

    public Image(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY)
    private Member member;

    public void setName(String name) {
        this.name = name;
    }
}
