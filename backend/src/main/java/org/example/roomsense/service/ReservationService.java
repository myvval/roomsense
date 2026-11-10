package org.example.roomsense.service;


import org.example.roomsense.dto.Slot;
import org.springframework.cglib.core.Local;
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
import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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

    @Transactional(readOnly = true)
    public List<Slot> getDailySchedule(Integer roomId, LocalDate date) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room was not found"));

        ZoneId zoneId = ZoneId.of("Europe/Prague");
        OffsetDateTime dayStart = date.atTime(7, 0).atZone(zoneId).toOffsetDateTime();
        OffsetDateTime dayEnd = date.atTime(20, 0).atZone(zoneId).toOffsetDateTime();

        List<Reservation> dailyReservations = reservationRepository.findBlockingReservationsForDay(roomId, dayStart, dayEnd);

        List<Slot> schedule = new ArrayList<>();

        for(int i=7;i<20;i++) {
            OffsetDateTime slotStart = date.atTime(i,0).atZone(zoneId).toOffsetDateTime();
            OffsetDateTime slotEnd = date.atTime(i+1,0).atZone(zoneId).toOffsetDateTime();
            boolean isFree = dailyReservations
                    .stream()
                    .noneMatch(r -> r.getStartAt().isBefore(slotEnd) && r.getEndAt().isAfter(slotStart));
            schedule.add(new Slot(slotStart, slotEnd, isFree));
        }
        return schedule;
    }

}
