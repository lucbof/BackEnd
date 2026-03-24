package com.broers.users.error;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        Instant timestamp,
        String path,
        String code,
        String message,
        List<FieldViolation> details
) {
    public record FieldViolation(String field, String issue) {}
}
