package org.example.roomsense.controller;

import org.example.roomsense.entity.Reservation;
import org.example.roomsense.repository.ReservationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;
    public ReservationController(ReservationRepository reservationRepository) {this.reservationRepository = reservationRepository;}

    @GetMapping
    public List<Reservation> findAllReservations() {return reservationRepository.findAll(); }
    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {return reservationRepository.save(reservation);}
}
