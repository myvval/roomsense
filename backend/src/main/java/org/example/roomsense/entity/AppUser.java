package org.example.roomsense.entity;

import jakarta.persistence.*;

@Entity
@Table(name="app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @Column(name = "card_uid", unique = true)
    private String cardUid;

    protected AppUser() {}
    public AppUser(String name, String cardUid) {
        this.name = name;
        this.cardUid = cardUid;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCardUid() {
        return cardUid;
    }
    public void setCardUid(String cardUid) {
        this.cardUid = cardUid;
    }
}
