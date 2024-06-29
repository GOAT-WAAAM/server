package com.goat.server.review.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
public record ReviewUpdateRequest(
        String title,
        String content,
        Boolean repeat,
        Boolean autoRepeat,
        List<String> reviewDates,
        LocalTime remindTime,
        LocalDate reviewStartDate,
        LocalDate reviewEndDate,
        Boolean postShare
) {
}