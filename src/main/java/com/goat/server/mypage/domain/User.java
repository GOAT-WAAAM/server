package com.goat.server.mypage.domain;

import com.goat.server.global.domain.BaseTimeEntity;
import com.goat.server.global.domain.ImageInfo;
import com.goat.server.global.domain.type.OauthProvider;
import com.goat.server.mypage.domain.type.Grade;
import com.goat.server.mypage.domain.type.Role;
import com.goat.server.mypage.domain.type.School;

import com.goat.server.mypage.dto.request.MypageDetailsRequest;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
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

    //마이페이지 회원 프로필 업데이트
    public void updateProfileImage(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }

    //마이페이지에서 세부 내용 업데이트 (닉네임, 학년, 전공)
    public void updateMypageDetails(MypageDetailsRequest request) {
        this.nickname = request.nickname();
        this.grade = request.grade();

        List<String> newMajorNames = request.majorList().stream()
                .map(String::valueOf)
                .toList();

        this.majorList.removeIf(existingMajor -> !newMajorNames.contains(existingMajor.getMajorName()));

        // 전공 추가
        for (String majorName : newMajorNames) {
            boolean exists = this.majorList.stream()
                    .anyMatch(existingMajor -> existingMajor.getMajorName().equals(majorName));

            if (!exists) {
                this.majorList.add(Major.builder()
                        .majorName(majorName)
                        .user(this)
                        .build());
            }
        }
    }

    //프로필 url 가져오는 메서드
    public String getProfileImageUrl() {
        if (this.imageInfo == null) {
            return "default-image-url";
        } else {
            return this.imageInfo.getImageUrl();
        }
    }

    public void updateOnBoardingInfo(String nickname, String goal) {
        this.nickname = nickname;
        this.goal = goal;
        this.role = Role.USER;
    }
}
