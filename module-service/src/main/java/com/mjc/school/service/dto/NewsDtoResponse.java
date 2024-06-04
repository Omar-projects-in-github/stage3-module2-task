package com.mjc.school.service.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public record NewsDtoResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createDate,
        LocalDateTime lastUpdatedDate,
        AuthorDtoResponse authorDto
) {
}