package com.goat.server.directory.domain;

import com.goat.server.global.domain.BaseTimeEntity;
import com.goat.server.mypage.domain.User;
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
    private Long directoryId;

    @Column(name = "directory_name", length = 100)
    private String directoryName;

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

    @Builder
    public Directory(String directoryName, String directoryColor, Long depth, User user, Directory parentDirectory) {
        this.directoryName = directoryName;
        this.directoryColor = directoryColor;
        this.depth = depth;
        this.user = user;
        this.parentDirectory = parentDirectory;
    }

    public void updateParentDirectory(Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
    }
}
