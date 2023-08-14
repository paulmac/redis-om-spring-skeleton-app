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

    // @Autowired
    @NonNull
    TradesRepository repo;

    @NonNull
    ScenariosRepository scenariosRepo;

    // private void merge(Trade trade, Trade exit, Scenario scenario) {
    // trade.setState(Trade.State.CLOSED);
    // trade.setStance(trade.getSignalType().endsWith("Long") ? Trade.Stance.LONG :
    // Trade.Stance.SHORT);
    // trade.setExitDateTime(exit.getEntryDateTime());
    // trade.setExitExecutionPrice(exit.getEntryExecutionPrice());
    // trade.setExitSignalPrice(exit.getEntrySignalPrice());
    // trade.setExitNarrative(exit.getEntryNarrative());
    // repo.save(trade);
    // scenario.trades.add(trade.getId());
    // }

    public void loadCsv(Scenario scenario, String filename) {

        try {
            Path path = Paths.get(ClassLoader.getSystemResource(filename).toURI());
            Reader reader = Files.newBufferedReader(path);
            CsvToBean<Trade> cb = new CsvToBeanBuilder<Trade>(reader)
                    .withType(Trade.class)
                    .build();
            ListIterator<Trade> itr = cb.parse().listIterator();
            // The first trade read, (i.e the last actual trade)

            // Trade exit = itr.next();
            // Trade next = itr.next();
            // Trade entry = exit.getNo() == next.getNo() ? next : null;
            // if (entry == null) {
            // exit = next;
            // entry = itr.next();
            // }
            // merge(entry, exit, scenario);
            // while (itr.hasNext()) {
            // // There is always a pair once the last trade is established as closed or not
            // exit = itr.next();
            // entry = itr.next();
            // merge(entry, exit, scenario);
            // }

            Trade entry; // itr.next();
            Trade exit; // = itr.next();
            while (itr.hasNext()) {
                entry = itr.next();
                scenario.current = entry;
                if (itr.hasNext()) {
                    exit = itr.next();
                    // merge(entry, exit, scenario);
                    entry.setState(Trade.State.CLOSED);
                    entry.setStance(entry.getSignalType().endsWith("Long") ? Trade.Stance.LONG : Trade.Stance.SHORT);
                    entry.setExitDateTime(exit.getEntryDateTime());
                    entry.setExitExecutionPrice(exit.getEntryExecutionPrice());
                    entry.setExitSignalPrice(exit.getEntrySignalPrice());
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
