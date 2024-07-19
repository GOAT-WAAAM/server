package com.goat.server.review.util;

import com.goat.server.global.application.S3Uploader;
import com.goat.server.review.domain.Review;
import jakarta.persistence.PreRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ReviewRemoveListener {

    private final S3Uploader s3Uploader;

    @Autowired
    public ReviewRemoveListener(S3Uploader s3Uploader) {
        this.s3Uploader = s3Uploader;
    }

    @PreRemove
    public void preRemove(Review target) {
        if (target.getImageInfo() != null) {
            s3Uploader.deleteImage(target.getImageInfo());
        }
    }
}
