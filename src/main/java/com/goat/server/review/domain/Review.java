package com.goat.server.review.domain;

import com.goat.server.global.domain.BaseTimeEntity;
import com.goat.server.global.domain.ImageInfo;
import com.goat.server.directory.domain.Directory;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Embedded
    private ImageInfo imageInfo;

    @Column(name = "image_title", length = 50)
    private String imageTitle;

    @Column(name = "is_image_enroll")
    private Boolean isImageEnroll;

    @Column(name = "content", length = 512)
    private String content;

    @Column(name = "is_repeatable")
    private Boolean isRepeatable;

    @Column(name = "remind_time")
    private LocalDateTime remindTime;

    @Column(name = "review_start_date")
    private LocalDate reviewStartDate;

    @Column(name = "review_end_date")
    private LocalDate reviewEndDate;

    @Column(name = "is_post_share")
    private Boolean isPostShare;

    @JoinColumn(name = "directory_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Directory directory;

    @Builder
    public Review(ImageInfo imageInfo, String imageTitle, Boolean isImageEnroll, String content, Boolean isRepeatable,
                  LocalDateTime remindTime, LocalDate reviewStartDate, LocalDate reviewEndDate, Boolean isPostShare,
                  Directory directory) {
        this.imageInfo = imageInfo;
        this.imageTitle = imageTitle;
        this.isImageEnroll = isImageEnroll;
        this.content = content;
        this.isRepeatable = isRepeatable;
        this.remindTime = remindTime;
        this.reviewStartDate = reviewStartDate;
        this.reviewEndDate = reviewEndDate;
        this.isPostShare = isPostShare;
        this.directory = directory;
    }
}
