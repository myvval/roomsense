package org.example.roomsense.repository;

import org.example.roomsense.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer>{
    Optional<AppUser> findByCardUid(String cardUid);

}
