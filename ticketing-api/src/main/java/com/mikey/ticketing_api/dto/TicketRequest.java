package com.mikey.ticketing_api.dto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketRequest(
        @NotBlank String subject,
        @NotBlank String description,
        @NotNull Integer requesterId,
        Integer assigneeId,
        @NotNull Integer categoryId,
        @NotNull Integer priorityId,
        @NotNull Integer statusId,
        OffsetDateTime resolvedAt
) {
}
