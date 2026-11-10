package org.example.roomsense.dto;

import java.time.OffsetDateTime;

public record Slot(
        OffsetDateTime start,
        OffsetDateTime end,
        boolean free
) {





}
