package com.redis.om.skeleton.services;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ListIterator;

import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.redis.om.skeleton.json.Scenario;
import com.redis.om.skeleton.json.Trade;
import com.redis.om.skeleton.repositories.ScenariosRepository;
import com.redis.om.skeleton.repositories.TradesRepository;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class TradesService {

    @NonNull
    TradesRepository repo;

    @NonNull
    ScenariosRepository scenariosRepo;

    public void loadCsv(Scenario scenario, String filename) {

        try {
            Path path = Paths.get(ClassLoader.getSystemResource(filename).toURI());
            Reader reader = Files.newBufferedReader(path);
            CsvToBean<Trade> cb = new CsvToBeanBuilder<Trade>(reader)
                    .withType(Trade.class)
                    .build();
            ListIterator<Trade> itr = cb.parse().listIterator();
            Trade entry; // itr.next();
            Trade exit; // = itr.next();
            while (itr.hasNext()) {
                entry = itr.next();
                scenario.current = entry;
                if (itr.hasNext()) {
                    exit = itr.next();
                    entry.setState(Trade.State.CLOSED);
                    entry.setStance(entry.getSignalType().endsWith("Long") ? Trade.Stance.LONG : Trade.Stance.SHORT);
                    entry.setExitAlertDateTime(exit.getEntryAlertDateTime());
                    entry.setExitExecutionPrice(exit.getEntryExecutionPrice());
                    entry.setExitAlertPrice(exit.getEntryAlertPrice());
                    entry.setExitNarrative(exit.getEntryNarrative());
                    repo.save(entry);
                    log.info("Trade Added: " + entry.toString());
                    scenario.trades.add(entry.getId());
                } else
                    break;
            }
            scenariosRepo.save(scenario); // Need to write Scenario to Redis inside this method
            reader.close();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            repo.deleteAllById(scenario.trades); // cleanup if failure
        }
    }

}
