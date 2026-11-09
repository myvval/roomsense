package org.example.roomsense.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record CreateReservationRequest(
        @NotNull Integer userId,
        @NotNull Integer roomId,
        @NotNull OffsetDateTime startAt,
        @NotNull OffsetDateTime endAt
) {







}
