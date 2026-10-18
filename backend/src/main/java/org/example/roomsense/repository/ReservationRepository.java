package org.example.roomsense.repository;

import org.example.roomsense.entity.AppUser;
import org.example.roomsense.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
}
