package org.example.roomsense.controller;

import org.example.roomsense.entity.AppUser;
import org.example.roomsense.repository.AppUserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {
    private final AppUserRepository appUserRepository;
    public AppUserController(AppUserRepository appUserRepository) {this.appUserRepository = appUserRepository;}

    @GetMapping
    public List<AppUser> findAllUsers() {
        return appUserRepository.findAll();
    }

    @PostMapping
    public AppUser createUser(@RequestBody AppUser appUser) {return appUserRepository.save(appUser);}

}
