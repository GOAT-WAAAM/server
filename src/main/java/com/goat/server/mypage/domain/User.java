package com.goat.server.mypage.domain;

import com.goat.server.global.domain.BaseTimeEntity;
import com.goat.server.global.domain.ImageInfo;
import com.goat.server.global.domain.type.OauthProvider;
import com.goat.server.mypage.domain.type.Grade;
import com.goat.server.mypage.domain.type.Role;
import com.goat.server.mypage.domain.type.School;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "school")
    private School school;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private Grade grade;

    @Embedded
    private ImageInfo imageInfo;

    @Column(name = "nickname", length = 30)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "social_id", length = 30)
    private String socialId;

    @Column(name = "email", length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private OauthProvider provider;

    @Column(name = "goal", length = 50)
    private String goal;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Major> majorList = new ArrayList<>();

    @Builder
    public User(School school, Grade grade, ImageInfo imageInfo, String nickname, Role role, String socialId,
                String email, OauthProvider provider, String goal, List<Major> majorList) {
        this.school = school;
        this.grade = grade;
        this.imageInfo = imageInfo;
        this.nickname = nickname;
        this.role = role;
        this.socialId = socialId;
        this.email = email;
        this.provider = provider;
        this.goal = goal;
        this.majorList = majorList;
    }

    //마이페이지 목표 업데이트
    public void updateGoal(String goal) {
        this.goal = goal;
    }
}
