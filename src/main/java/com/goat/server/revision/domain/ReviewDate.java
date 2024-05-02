package com.goat.server.revision.domain;

import com.goat.server.revision.domain.type.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class ReviewDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "date_id")
    private Long dateId;

    @Enumerated(EnumType.STRING)
    private Date date;

    @JoinColumn(name = "review_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    @Builder
    public ReviewDate(Date date, Review review) {
        this.date = date;
        this.review = review;
    }
}
