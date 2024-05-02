package com.goat.server.subject.domain;

import com.goat.server.global.domain.BaseTimeEntity;
import com.goat.server.mypage.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Subject extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "subject_name", length = 50)
    private String subjectName;

    @Column(name = "subject_color", length = 100)
    private String subject_color;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Subject(String subjectName, String subject_color, User user) {
        this.subjectName = subjectName;
        this.subject_color = subject_color;
        this.user = user;
    }
}
