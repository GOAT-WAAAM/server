package com.goat.server.notification.domain;

import com.goat.server.global.domain.BaseTimeEntity;
import com.goat.server.mypage.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class NotificationSetting extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "setting_id")
    private Long settingId;

    @Column(name = "is_review_noti")
    private Boolean isReviewNoti;

    @Column(name = "is_post_noti")
    private Boolean isPostNoti;

    @Column(name = "is_comment_noti")
    private Boolean isCommentNoti;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public NotificationSetting(Boolean isReviewNoti, Boolean isPostNoti, Boolean isCommentNoti, User user) {
        this.isReviewNoti = isReviewNoti;
        this.isPostNoti = isPostNoti;
        this.isCommentNoti = isCommentNoti;
        this.user = user;
    }

    //복습 알림 권한 수정
    public void updateReviewNoti(Boolean isReviewNoti) {
        this.isReviewNoti = isReviewNoti;
    }
}
