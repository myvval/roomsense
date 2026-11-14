package org.example.roomsense.dto;

public record ScanResponse(
        String action,
        Integer userId,
        String userName,
        Integer roomId,
        String reservationStatus,
        String message
) {
}
