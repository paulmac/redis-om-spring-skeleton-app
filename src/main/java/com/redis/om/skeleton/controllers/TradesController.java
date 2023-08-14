package com.redis.om.skeleton.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.om.skeleton.json.Trade;
import com.redis.om.skeleton.repositories.ScenariosRepository;
import com.redis.om.skeleton.repositories.TradesRepository;
import com.redis.om.skeleton.services.TradesService;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@RestController
@RequestMapping("/ibkrhub/trades/")
public class TradesController {

    @NonNull
    TradesService service;

    @NonNull
    TradesRepository repo;

    @NonNull
    ScenariosRepository scenariosRepo;

    // *** DANGER ****
    @DeleteMapping("all")
    void deleteAll() {
        repo.deleteAll();
    }

    // @DeleteMapping("clean")
    // void cleanByScenarioId(
    // @RequestParam("strategy_id") String strategyId) {
    // Iterable<Trade> trades = repo.findByScenarioId(strategyId);
    // for (Trade trade : trades)
    // repo.delete(trade);
    // }

    @GetMapping("{id}")
    Optional<Trade> byId(@PathVariable String id) {
        return repo.findById(id);
    }

    // @GetMapping("scenario")
    // Iterable<Trade> byCompositeId(@RequestParam("short_name") String shortName,
    // @RequestParam("symbol") String symbol,
    // @RequestParam("time_frame") String timeFrame,
    // @RequestParam("run_name") String runName) {

    // Optional<Scenario> scenario =
    // scenariosRepo.findByShortNameAndSymbolAndTimeFrameAndRunName(shortName,
    // symbol,
    // timeFrame,
    // runName);
    // if (scenario.isPresent())
    // return repo.findAllById(scenario.get().trades);

    // return Collections.emptyList();
    // }

    // @GetMapping("scenario_id")
    // Iterable<Trade> byScenarioId(@RequestParam("scenario_id") String scenarioId)
    // {
    // return repo.findByScenarioId(scenarioId);
    // }

    // *** NOT USEFUL ***/
    @GetMapping("all")
    Iterable<Trade> all() {
        return repo.findAll();
        // Iterable<Trade> trades = repo.findAll();
        // for (Trade trade : trades) {
        // log.info("Trade : " + trade.toString());
        // }
        // return trades;
    }
}
