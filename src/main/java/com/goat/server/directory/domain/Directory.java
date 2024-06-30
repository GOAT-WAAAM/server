package com.goat.server.directory.domain;

import com.goat.server.global.domain.BaseTimeEntity;
import com.goat.server.mypage.domain.User;
import com.goat.server.review.domain.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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
    private Long id;

    @Column(name = "directory_title", length = 100)
    private String title;

    @Column(name = "directory_color", length = 50)
    private String directoryColor;

    @Column(name = "depth")
    private Long depth;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "parent_directory_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Directory parentDirectory;

    @OneToMany(mappedBy = "parentDirectory", cascade = CascadeType.REMOVE)
    private List<Directory> childDirectoryList = new ArrayList<>();

    @OneToMany(mappedBy = "directory", cascade = CascadeType.REMOVE)
    private List<Review> reviewList = new ArrayList<>();

    @Builder
    public Directory(String title, String directoryColor, Long depth, User user, Directory parentDirectory) {
        this.title = title;
        this.directoryColor = directoryColor;
        this.depth = depth;
        this.user = user;
        this.parentDirectory = parentDirectory;
    }

    public void updateParentDirectory(Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
        this.depth = parentDirectory.getDepth() + 1;
    }

    public void updateModifiedDate() {
        super.updateModifiedDate();
    }

    public void touchParentDirectories() {
        updateModifiedDate();

        if (this.parentDirectory != null) {
            this.parentDirectory.touchParentDirectories();
        }
    }

    public void validateUser(Long userId) {
        if (!this.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("해당 폴더에 대한 권한이 없습니다.");
        }
    }
}
