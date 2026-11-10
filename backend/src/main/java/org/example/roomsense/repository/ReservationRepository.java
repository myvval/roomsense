package org.example.roomsense.repository;

import org.example.roomsense.entity.AppUser;
import org.example.roomsense.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId " +
            "AND r.statusCode IN ('PENDING', 'CONFIRMED', 'ACTIVE', 'OVERSTAY') " +
            "AND r.startAt < :dayEnd AND r.endAt > :dayStart")
    List<Reservation> findBlockingReservationsForDay(
            @Param("roomId") Integer roomId,
            @Param("dayStart") OffsetDateTime dayStart,
            @Param("dayEnd") OffsetDateTime dayEnd
    );

}

