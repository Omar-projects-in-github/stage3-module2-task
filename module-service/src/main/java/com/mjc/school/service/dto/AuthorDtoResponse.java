package com.mjc.school.service.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public record AuthorDtoResponse(
        Long id,
        String name,
        LocalDateTime createDate,
        LocalDateTime lastUpdatedDate) {
}