package org.example.roomsense.entity;


import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name="reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(optional=false)
    @JoinColumn(name="user_id", nullable = false)
    private AppUser appUser;
    @ManyToOne(optional = false)
    @JoinColumn(name="room_id", nullable= false)
    private Room room;
    @Column(name="status_code", nullable = false)
    private String statusCode = "PENDING"; //Change later to Status object.
    @Column(name="checked_in_at")
    private OffsetDateTime checkedInAt;
    @Column(name="checked_out_at")
    private OffsetDateTime checkedOutAt;
    @Column(name="start_at",nullable = false)
    private OffsetDateTime startAt;
    @Column(name="end_at",nullable = false)
    private OffsetDateTime endAt;

    protected Reservation() {}
    public Reservation(Room room, AppUser appUser, OffsetDateTime startAt, OffsetDateTime endAt) {
        this.room = room;
        this.appUser = appUser;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }
    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }

    public String getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public OffsetDateTime getCheckedInAt() {
        return checkedInAt;
    }
    public void setCheckedInAt(OffsetDateTime checkedInAt) {
        this.checkedInAt = checkedInAt;
    }

    public OffsetDateTime getCheckedOutAt() {
        return checkedOutAt;
    }
    public void setCheckedOutAt(OffsetDateTime checkedOutAt) {
        this.checkedOutAt = checkedOutAt;
    }

    public OffsetDateTime getStartAt() {
        return startAt;
    }
    public void setStartAt(OffsetDateTime startAt) {
        this.startAt = startAt;
    }

    public OffsetDateTime getEndAt() {
        return endAt;
    }
    public void setEndAt(OffsetDateTime endAt) {
        this.endAt = endAt;
    }
}