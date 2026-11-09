package org.example.roomsense.dto;

import jakarta.validation.constraints.NotNull;
import org.example.roomsense.entity.Reservation;

import java.time.OffsetDateTime;

public record ReservationResponse(
        Integer id,
        Integer userId,
        Integer roomId,
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        String statusCode //Change later to Status object

) {
    public static ReservationResponse fromEntity(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getAppUser().getId(),
                reservation.getRoom().getId(),
                reservation.getStartAt(),
                reservation.getEndAt(),
                reservation.getStatusCode()
        );
    }


}
