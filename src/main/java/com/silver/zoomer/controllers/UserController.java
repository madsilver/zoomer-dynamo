package com.silver.zoomer.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.silver.zoomer.repositories.UserRepository;
import com.silver.zoomer.entities.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private UserRepository repository;

    @Autowired
    public UserController(final UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        log.info("Getting all users");

        List<User> users = new ArrayList<>();
        this.repository.findAll().iterator().forEachRemaining(users::add);

        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> post(@RequestBody final User newUser) {
        log.info("Posting new user");

        User user = this.repository.save(newUser);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}

