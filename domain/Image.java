package com.healthyapplication.healthyapplication.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY)
    private Member member;
}
