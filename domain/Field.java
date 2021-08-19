package com.healthyapplication.healthyapplication.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Field {

    public Field(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "field_id")
    private Long id;

    private String name;
    private int hour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //== 시간 추가 ==//
    public void addTime(int hour) {
        this.hour += hour;
    }

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

}
