package com.healthyapplication.healthyapplication.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class Community {

    public Community(LocalDateTime date, String title, String content) {
        this.date = date;
        this.title = title;
        this.content = content;
    }

    @Id
    @GeneratedValue
    @Column(name = "community_id")
    private Long id;

    private LocalDateTime date;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 업데이트
    public void update(Community community) {
        this.date = community.getDate();
        this.title = community.getTitle();
        this.content = community.getContent();
    }

    // 멤버 세팅
    public void setMember (Member member) {
        this.member = member;
    }

}
