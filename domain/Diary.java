package com.healthyapplication.healthyapplication.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
public class Diary {

    public Diary(LocalDateTime date, String content) {
        this.date = date;
        this.content = content;
    }

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
