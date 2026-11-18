package org.example.roomsense.service;

import org.example.roomsense.dto.ScanRequest;
import org.example.roomsense.dto.ScanResponse;
import org.example.roomsense.entity.AppUser;
import org.example.roomsense.entity.Reservation;
import org.example.roomsense.entity.Room;
import org.example.roomsense.repository.AppUserRepository;
import org.example.roomsense.repository.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.roomsense.repository.RoomRepository;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ScanService {
    private final ReservationRepository reservationRepository;
    private final AppUserRepository appUserRepository;
    private final RoomRepository roomRepository;


    public ScanService(ReservationRepository reservationRepository, AppUserRepository appUserRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.appUserRepository = appUserRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public ScanResponse handleScan(Integer roomId, ScanRequest request) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        AppUser appUser = appUserRepository.findByCardUid(cleanString(request.cardUid()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that card not found"));

        OffsetDateTime now = OffsetDateTime.now();

        List<Reservation> upcomingReservations = reservationRepository.findCurrentAndUpcomingReservations(roomId, now, now.plusMinutes(15));

        Optional<Reservation> userReservation = upcomingReservations.stream()
                .filter(reservation -> reservation.getAppUser().getId().equals(appUser.getId()))
                .findFirst();


        if (userReservation.isPresent()) {

            Reservation reservation = userReservation.get();
            if (reservation.getStatusCode().equals("CONFIRMED")) {
                reservation.setStatusCode("ACTIVE");
                reservation.setCheckedInAt(now);
                Reservation saved = reservationRepository.saveAndFlush(reservation);

                return new ScanResponse("CHECK_IN", appUser.getId(), appUser.getName(), roomId, "ACTIVE", "User checked in successfully.");

            } else if (reservation.getStatusCode().equals("ACTIVE")) {
                reservation.setStatusCode("COMPLETED");
                reservation.setCheckedOutAt(now);
                reservation.setEndAt(now);
                reservationRepository.saveAndFlush(reservation);

                return new ScanResponse("CHECK_OUT", appUser.getId(), appUser.getName(), roomId, "COMPLETED", "User checked out successfully.");

            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Reservation is not in a scannable state");
            }
        } else {
            if (!upcomingReservations.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Room is currently in use");
            }

            Reservation newReservation = new Reservation(room, appUser, now, now.plusHours(1));
            newReservation.setStatusCode("ACTIVE");
            newReservation.setCheckedInAt(now);
            Reservation saved = reservationRepository.saveAndFlush(newReservation);

            return new ScanResponse("AD_HOC", appUser.getId(), appUser.getName(), roomId, "ACTIVE", "Ad-hoc reservation created successfully.");
        }
    }



    private String cleanString(String string) {
        return string.trim().toUpperCase().replace(" ", "");
    }
}
