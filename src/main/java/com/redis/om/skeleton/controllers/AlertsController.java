package com.redis.om.skeleton.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.om.skeleton.json.Alert;
import com.redis.om.skeleton.repositories.AlertsRepository;
import com.redis.om.skeleton.services.AlertService;

import lombok.Data;
import lombok.NonNull;

@Data
@RestController
@RequestMapping("/ibkrhub/alerts/")
public class AlertsController {

    @NonNull
    private AlertsRepository repo;

    @NonNull
    private AlertService alertService;

    @GetMapping("all")
    Iterable<Alert> all() {
        return repo.findAll();
    }

    @GetMapping("{id}")
    Optional<Alert> byId(@PathVariable String id) {
        return repo.findById(id);
    }

    @PostMapping("new")
    Alert create(@RequestBody Alert alert) {
        alertService.post(alert);
        return repo.save(alert);
    }
}
