package com.healthyapplication.healthyapplication.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class Community {

    @Id
    @GeneratedValue
    @Column(name = "community_id")
    private Long id;

    private LocalDateTime date;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void update(Community community) {
        this.date = community.getDate();
        this.content = community.getContent();
    }

}
