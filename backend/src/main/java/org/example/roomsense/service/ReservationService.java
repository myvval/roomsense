package org.example.roomsense.service;


import org.springframework.transaction.annotation.Transactional;
import org.example.roomsense.dto.CreateReservationRequest;
import org.example.roomsense.dto.ReservationResponse;
import org.example.roomsense.entity.AppUser;
import org.example.roomsense.entity.Reservation;
import org.example.roomsense.entity.Room;
import org.example.roomsense.repository.AppUserRepository;
import org.example.roomsense.repository.ReservationRepository;
import org.example.roomsense.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final AppUserRepository appUserRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              AppUserRepository appUserRepository,
                              RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.appUserRepository = appUserRepository;
        this.roomRepository = roomRepository;

    }

    @Transactional
    public ReservationResponse create(CreateReservationRequest createReservationRequest) {
        if (!createReservationRequest.endAt().isAfter(createReservationRequest.startAt())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End must be after start");
        }
        AppUser user = appUserRepository.findById(createReservationRequest.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found"));

        Room room = roomRepository.findById(createReservationRequest.roomId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room was not found"));

        Reservation reservation = new Reservation(room, user, createReservationRequest.startAt(), createReservationRequest.endAt());
        reservation.setStatusCode("CONFIRMED");
        Reservation savedReservation = reservationRepository.saveAndFlush(reservation);
        return ReservationResponse.fromEntity(savedReservation);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::fromEntity)
                .toList();


    }




}
