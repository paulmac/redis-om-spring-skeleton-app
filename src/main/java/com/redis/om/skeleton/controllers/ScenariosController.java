package com.redis.om.skeleton.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.om.skeleton.repositories.ScenariosRepository;

import lombok.NonNull;

//@Slf4j
@RestController
@RequestMapping("/ibkrhub/scenarios/")
public class ScenariosController {

    // @Autowired
    // ScenariosService service;

    // @Autowired
    // TradesService tradesService;

    // @Autowired
    @NonNull
    ScenariosRepository repo;

    // @Autowired
    // StrategiesRepository strategiesRepo;

    // @Autowired
    // TradesRepository tradesRepo;

    // @GetMapping("csv")
    // Optional<Scenario> scenarioCsv(
    // @RequestParam("strategy_name") String strategyName,
    // @RequestParam("symbol") String symbol,
    // @RequestParam("timeframe") String timeFrame,
    // @RequestParam("runname") String runName,
    // @RequestParam("summary_file") String summaryFile,
    // @RequestParam("props_file") String propsFile,
    // @RequestParam("trades_file") String tradesFile) {
    // // return service.scenarioCsv(strategyName, symbol, timeFrame, runName,
    // summaryFile, propsFile, tradesFile);
    // }

    // @GetMapping("all")
    // Iterable<Scenario> all() {
    // return repo.findAll();
    // }

    // @GetMapping("{id}")
    // Optional<Scenario> byId(@PathVariable String id) {
    // return repo.findById(id);
    // }

    // @PostMapping("new")
    // Scenario create( // ResponseEntity<Object>
    // @RequestParam("strategy_name") String strategyName,
    // @RequestParam("symbol") String symbol,
    // @RequestParam("timeframe") String timeFrame,
    // @RequestParam("runname") String runName) {
    // Scenario scenario = new Scenario(strategyName, symbol, timeFrame, runName);
    // Optional<Strategy> strategy = strategiesRepo.findByName(strategyName);
    // if (strategy.isPresent()) {
    // log.info("Strategy : " + strategy.get().getId());
    // // .ifPresent(strategy -> repo
    // // // .findByStrategyNameAndSymbolAndTimeFrameAndRunName(strategyName,
    // symbol,
    // // // timeFrame, runName)
    // // .findByStrategyName(strategyName)
    // // .ifPresentOrElse(
    // // s -> log.info("Scenario already exists : " + s.getId()),
    // // () -> {
    // repo.save(scenario);
    // strategy.get().getScenarios().add(scenario);
    // strategiesRepo.save(strategy.get());
    // log.info("Scenario new : " + scenario.getId());
    // // })

    // // );
    // }
    // return scenario;
    // // return scenario.getId() != null ? ResponseEntity.ok().body(scenario)
    // // : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError());
    // }

    // @DeleteMapping("{id}")
    // void delete(@PathVariable String id) {

    // // Template
    // // repo.findById(id).ifPresentOrElse(
    // // s -> log.info("Scenario : " + s.getId()),
    // // () -> log.info("Scenario not found : ")
    // // );

    // repo.findById(id).ifPresentOrElse(
    // s -> {
    // // log.info("Found Scenario ID : " + s.getId());
    // tradesService.deleteAll(tradesRepo.findByScenarioId(id));
    // repo.delete(s);
    // },
    // () -> log.info("Scenario not found : "));
    // }

    @DeleteMapping("all")
    void deleteAll() {
        repo.deleteAll();
    }

}
