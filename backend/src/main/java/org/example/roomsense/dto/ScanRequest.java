package org.example.roomsense.dto;

import jakarta.validation.constraints.NotBlank;

public record ScanRequest(
        @NotBlank(message = "Card UID must not be empty.")
        String cardUid
) {
}
