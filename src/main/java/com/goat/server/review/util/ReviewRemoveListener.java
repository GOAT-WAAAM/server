package com.goat.server.review.util;

import com.goat.server.global.application.S3Uploader;
import com.goat.server.global.util.ApplicationContextProvider;
import com.goat.server.review.domain.Review;
import jakarta.persistence.PreRemove;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@Component
public class ReviewRemoveListener {

    private static S3Uploader s3Uploader;

    @PreRemove
    public void preRemove(Review target) {
        if (s3Uploader == null) {
            s3Uploader = ApplicationContextProvider.getApplicationContext().getBean(S3Uploader.class);
        }

        if (s3Uploader != null && target.getImageInfo() != null) {
            s3Uploader.deleteImage(target.getImageInfo());
        }
    }
}
