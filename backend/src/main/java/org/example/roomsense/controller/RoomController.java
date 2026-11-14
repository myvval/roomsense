package org.example.roomsense.controller;

import jakarta.validation.Valid;
import org.example.roomsense.dto.ScanRequest;
import org.example.roomsense.dto.ScanResponse;
import org.example.roomsense.dto.Slot;
import org.example.roomsense.entity.Room;
import org.example.roomsense.repository.RoomRepository;
import org.example.roomsense.service.ReservationService;
import org.example.roomsense.service.ScanService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomRepository roomRepository;
    private final ReservationService reservationService;
    private final ScanService scanService;


    public RoomController(RoomRepository roomRepository, ReservationService reservationService, ScanService scanService) {
        this.roomRepository = roomRepository;
        this.reservationService = reservationService;
        this.scanService = scanService;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomRepository.save(room);
    }


    @GetMapping("/{id}/schedule")
    public List<Slot> getRoomSchedule(@PathVariable Integer id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return reservationService.getDailySchedule(id, date);
    }

    @PostMapping("/{id}/scan")
    public ScanResponse scanRoom(@PathVariable Integer id, @Valid @RequestBody ScanRequest request) {
        return scanService.handleScan(id, request);
    }




}