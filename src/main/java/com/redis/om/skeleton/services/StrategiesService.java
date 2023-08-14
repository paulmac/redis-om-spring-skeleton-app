package com.redis.om.skeleton.services;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.redis.om.skeleton.json.Scenario;
import com.redis.om.skeleton.json.Strategy;
import com.redis.om.skeleton.repositories.ScenariosRepository;
import com.redis.om.skeleton.repositories.StrategiesRepository;
import com.redis.om.skeleton.repositories.TradesRepository;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Service
public class StrategiesService {

    // @Autowired
    @NonNull
    ScenariosRepository repo;

    // @Autowired
    @NonNull
    StrategiesRepository strategiesRepo;

    @NonNull
    ScenariosRepository scenariosRepo;

    // @Autowired
    @NonNull
    TradesRepository tradesRepo;

    // @Autowired
    @NonNull
    TradesService tradesService;

    public void deleteAll() {
        strategiesRepo.deleteAll();
        tradesRepo.deleteAll();
        scenariosRepo.deleteAll();
    }

    public void deleteDirection(Scenario.Direction direction, Strategy s) {
        switch (direction) {
            case BACK:
                deleteScenarios(scenariosRepo.findAllById(s.backs));
                s.backs.clear();
                strategiesRepo.save(s);
                break;
            case FORWARD:
                deleteScenarios(scenariosRepo.findAllById(s.forwards));
                s.forwards.clear();
                strategiesRepo.save(s);
                break;
            case ALL:
                deleteScenarios(scenariosRepo.findAllById(s.backs));
                deleteScenarios(scenariosRepo.findAllById(s.forwards));
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Direction Paramter");
        }
    }

    public void deleteScenarios(Iterable<Scenario> scenarios) {
        for (Scenario scenario : scenarios)
            tradesRepo.deleteAllById(scenario.trades); // in 'bulk' deletion of all Trades associated with each scenario
        scenariosRepo.deleteAll(scenarios);
    }

    public Iterable<Strategy> loadStatic() {
        String trendiloLongName = "Trendilo AGX T3 9MA ATR";
        String trendiloShortName = "Trendilo";
        String trendiloDesc = "A Secret indicator of Davedd confirmed by AGX and T3 Stop start of Bar TP 9ma";
        String trendiloYouTubeURL = "https://www.youtube.com/watch?v=lLExNc4Jhck";
        String pmacRegressionLongName = "Pmac Regression Simon Myfxbook";
        String pmacRegressionShortName = "Regression";
        String pmacRegressionDesc = "Simon MyFxbook top 1% Trader via Trapped Trader";
        String scenarioForwardNarrative = "My Baseline Forward";

        Iterable<Strategy> strategies = strategiesRepo.findAll();
        if (strategies.iterator().hasNext()) {
            log.info("Already Loaded Strategy : " + strategies.iterator().next().toString());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Strategies already exist");
        } else {
            Scenario trendiloForward = new Scenario();
            trendiloForward.setDirection(Scenario.Direction.FORWARD);
            trendiloForward.setTimeFrame(Scenario.TimeFrame.M15);
            trendiloForward.setSecType("FX");
            trendiloForward.setCurrency("USD");
            trendiloForward.setNarrative(scenarioForwardNarrative);
            trendiloForward.setLongName(trendiloLongName);
            Strategy trendilo = Strategy.of(trendiloLongName, trendiloShortName, "Davedd", trendiloDesc,
                    trendiloYouTubeURL);

            Scenario pmacRegressionForward = new Scenario();
            pmacRegressionForward.setDirection(Scenario.Direction.FORWARD);
            pmacRegressionForward.setTimeFrame(Scenario.TimeFrame.H4);
            pmacRegressionForward.setSecType("FX");
            pmacRegressionForward.setCurrency("EUR");
            pmacRegressionForward.setNarrative(scenarioForwardNarrative);
            pmacRegressionForward.setLongName(pmacRegressionLongName);
            Strategy pmacRegression = Strategy.of(pmacRegressionLongName, pmacRegressionShortName, "SimonMyFxbook",
                    pmacRegressionDesc, "dissapeared");

            scenariosRepo.saveAll(List.of(trendiloForward, pmacRegressionForward)); // This generates an ID and saves in
                                                                                    // Redis
                                                                                    // Key Storage
            trendilo.forwards.add(trendiloForward.getId());
            pmacRegression.forwards.add(pmacRegressionForward.getId());

            return strategiesRepo.saveAll(List.of(trendilo, pmacRegression));
        }
    }

    public Map<String, String> loadPropertiesCsv(String fileName) {

        HashMap<String, String> props = new HashMap<>();

        try {
            Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
            Reader reader = Files.newBufferedReader(path);
            CSVReader csvReader = new CSVReader(reader);

            String[] line = csvReader.readNext(); // discard first line
            while (line != null && csvReader.peek() != null) {
                line = csvReader.readNext();
                if (!props.containsKey(line[0]))
                    props.put(line[0], line[1]);
                log.info("Pair : " + line[0] + ", " + line[1]);

            }
            csvReader.close();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return props;
    }

    public Optional<Strategy> loadCsv(String longName, String symbol, String summaryFile, String propsFile,
            String tradesFile) {

        try {
            Path path = Paths.get(ClassLoader.getSystemResource(summaryFile).toURI());
            try (Reader reader = Files.newBufferedReader(path)) {

                // FOR TESTING
                // CsvToBean<Scene> c = new CsvToBeanBuilder<Scene>(reader)
                // .withType(Scene.class)
                // .build();
                // List<Scene> l = c.parse();
                // Iterator<Scene> i = l.iterator();
                // while (i.hasNext())
                // log.info("nb : " + i.next().toString());

                CsvToBean<Scenario> cb = new CsvToBeanBuilder<Scenario>(reader)
                        .withType(Scenario.class)
                        .build();
                List<Scenario> list = cb.parse();
                ListIterator<Scenario> itr = list.listIterator();
                // Build the main section using the first row
                Scenario scenario = itr.next();
                Scenario percentages = itr.next();

                Optional<Strategy> strategy = strategiesRepo.findByLongName(longName); // Unique
                strategy.ifPresentOrElse(
                        s -> {
                            log.info("Strategy {" + s.getLongName() + "}, backs.size=" + s.backs.size());
                            if (s.backs.isEmpty()) {
                                scenario.setSymbol(symbol);
                                scenario.setNarrative("My First Backtest");
                                scenario.setNetProfitPercentage(percentages.getNetProfit());
                                scenario.setGrossLossPercentage(percentages.getGrossLoss());
                                scenario.setGrossProfitPercentage(percentages.getGrossProfit());
                                scenario.setMaxRunUpPercentage(percentages.getMaxRunUp());
                                scenario.setMaxDrawdownPercentage(percentages.getMaxDrawdown());
                                scenario.setBuyAndHoldReturnPercentage(percentages.getBuyAndHoldReturn());
                                scenario.setOpenPnLPercentage(percentages.getOpenPnL());
                                scenario.setAvgTradePercentage(percentages.getAvgTrade());
                                scenario.setAvgWinningTradePercentage(percentages.getAvgWinningTrade());
                                scenario.setAvgLosingTradePercentage(percentages.getAvgLosingTrade());
                                scenario.setLargestWinningTradePercentage(percentages.getLargestWinningTrade());
                                scenario.setLargestLosingTradePercentage(percentages.getLargestLosingTrade());
                                scenario.setProperties(loadPropertiesCsv(propsFile));
                                tradesService.loadCsv(scenario, tradesFile);
                                s.backs.add(scenario.getId());
                                strategiesRepo.save(s); // commit the new Scenario
                            }
                        },
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Strategy not found : " + longName);
                        });

                return strategy;
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return Optional.empty();
        }
    }

}
