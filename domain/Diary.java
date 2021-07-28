package com.healthyapplication.healthyapplication.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
public class Diary {
    @Id
    @GeneratedValue
    @Column(name = "diary_id")
    private String id;

    private LocalDateTime date;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
