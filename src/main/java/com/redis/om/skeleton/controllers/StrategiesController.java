package com.redis.om.skeleton.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.redis.om.skeleton.json.Scenario;
import com.redis.om.skeleton.json.Strategy;
import com.redis.om.skeleton.repositories.ScenariosRepository;
import com.redis.om.skeleton.repositories.StrategiesRepository;
import com.redis.om.skeleton.repositories.TradesRepository;
import com.redis.om.skeleton.services.StrategiesService;
import com.redis.om.skeleton.services.TradesService;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@RestController
@RequestMapping("/ibkrhub/strategies")
public class StrategiesController {

    // @Autowired - NOT USED AS ctor init safer
    @NonNull
    StrategiesRepository repo;

    @NonNull
    ScenariosRepository scenariosRepo;

    @NonNull
    TradesRepository tradesRepo;

    @NonNull
    StrategiesService service;

    @NonNull
    TradesService tradesService;

    @GetMapping("{id}")
    Optional<Strategy> byId(@PathVariable String id) {
        return repo.findById(id);
    }

    @GetMapping("short_name")
    Iterable<Strategy> name(@RequestParam("short_name") String shortName) {
        return repo.findByShortName(shortName);
    }

    @GetMapping("all")
    Iterable<Strategy> all() {
        return repo.findAll();
    }

    @PostMapping("new")
    Strategy create(@RequestBody Strategy strategy) {
        return repo.save(strategy);
    }

    @GetMapping("load_forward_static")
    Iterable<Strategy> load() {
        return service.loadStatic();
    }

    @GetMapping("load_back_csv")
    Optional<Strategy> loadCsv(
            @RequestParam("long_name") String longName,
            @RequestParam("symbol") String symbol,
            @RequestParam("summary_file") String summaryFile,
            @RequestParam("props_file") String propsFile,
            @RequestParam("trades_file") String tradesFile) {
        return service.loadCsv(longName, symbol, summaryFile, propsFile, tradesFile);
    }

    @DeleteMapping("scenarios")
    void deleteBack(
            @RequestParam("long_name") String longName,
            @RequestParam("direction") Scenario.Direction direction) {
        repo.findByLongName(longName).ifPresentOrElse(
                s -> {
                    log.info("DELETE Strategy : " + longName);
                    service.deleteDirection(direction, s);
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Strategy not found");
                });
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable String id) {
        repo.findById(id).ifPresentOrElse(
                s -> {
                    log.info("DELETE Strategy : " + s.toString());
                    service.deleteDirection(Scenario.Direction.ALL, s);
                    repo.delete(s);
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Strategy not found");
                });
    }

    @DeleteMapping("all")
    void deleteAll() {
        service.deleteAll();
    }
}
