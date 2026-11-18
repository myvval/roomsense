package org.example.roomsense.service;


import org.example.roomsense.dto.ScanRequest;
import org.example.roomsense.entity.Room;
import org.example.roomsense.repository.AppUserRepository;
import org.example.roomsense.repository.ReservationRepository;
import org.example.roomsense.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScanServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private ScanService scanService;

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("Returns 404 when room doesnt exists.")
    public void handleScan_whenRoomNotFound_throws404() {

        int roomId = 1;
        ScanRequest scanRequest = new ScanRequest("CARD_123");


        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> scanService.handleScan(roomId, scanRequest)
        ).isInstanceOfSatisfying(ResponseStatusException.class, exception -> {
            assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(exception.getReason()).isEqualTo("Room not found");
        });

        verify(roomRepository).findById(roomId);
        verifyNoInteractions(appUserRepository, reservationRepository);


    }

    @Test
    @DisplayName("Returns 404 when card is not registered")
    public void handleScan_whenCardNotRegistered_throws404() {


        int roomId = 1;


        Room room = new Room("prvni", false);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        ScanRequest scanRequest = new ScanRequest("CARD_123");

        when(appUserRepository.findByCardUid(scanRequest.cardUid())).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> scanService.handleScan(roomId, scanRequest))
                .isInstanceOfSatisfying(ResponseStatusException.class, exception -> {
                    assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                    assertThat(exception.getReason()).isEqualTo("User with that card not found");
                });

        verify(roomRepository).findById(roomId);
        verify(appUserRepository).findByCardUid(scanRequest.cardUid());
        verifyNoInteractions(reservationRepository);

    }


}
