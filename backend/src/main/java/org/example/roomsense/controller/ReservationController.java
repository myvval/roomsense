package org.example.roomsense.controller;

import jakarta.validation.Valid;
import org.example.roomsense.dto.CreateReservationRequest;
import org.example.roomsense.dto.ReservationResponse;
import org.example.roomsense.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    public ReservationController(ReservationService reservationService) {this.reservationService = reservationService;}

    @GetMapping
    public List<ReservationResponse> findAllReservations() {return reservationService.findAll(); }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReservationResponse createReservation(@Valid @RequestBody CreateReservationRequest createReservationRequest) {
        return reservationService.create(createReservationRequest);
    }
}
