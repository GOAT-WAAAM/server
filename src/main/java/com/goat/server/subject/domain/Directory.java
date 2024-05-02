package com.goat.server.subject.domain;

import com.goat.server.global.domain.BaseTimeEntity;
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
public class Directory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "directory_id")
    private Long directoryId;

    @Column(name = "directory_name", length = 100)
    private String directoryName;

    @JoinColumn(name = "subject_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    @Builder
    public Directory(String directoryName, Subject subject) {
        this.directoryName = directoryName;
        this.subject = subject;
    }
}
