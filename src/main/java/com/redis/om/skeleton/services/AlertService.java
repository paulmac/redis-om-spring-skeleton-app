package com.redis.om.skeleton.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.redis.om.skeleton.json.Alert;
import com.redis.om.skeleton.repositories.ScenariosRepository;
import com.redis.om.skeleton.repositories.TradesRepository;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class AlertService {

    @NonNull
    TradesRepository repo;

    @NonNull
    ScenariosRepository scenariosRepo;

    public void post(Alert alert) {

        scenariosRepo.findByLongNameAndSymbolAndTimeFrameAndDirection(alert.getStrategy(), alert.getSymbol(),
                alert.getTimeFrame(), alert.getDirection()).ifPresentOrElse(
                        s -> {
                            log.info("Found Scenario : " + alert.getStrategy());
                            switch (s.current.state) {
                                case OPEN:
                                    if (StringUtils.startsWithIgnoreCase(alert.getDescription(), "Close")) {
                                        s.current.setExitSignalPrice(alert.getPrice());
                                        s.current.setExitDateTime(alert.getTimeStamp());
                                    } else
                                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trade not OPEN");
                                    break;
                                case BLANK:
                                    if (StringUtils.startsWithIgnoreCase(alert.getDescription(), "Open")) {
                                        s.current.setEntrySignalPrice(alert.getPrice());
                                        s.current.setEntryDateTime(alert.getTimeStamp());
                                    } else
                                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trade not BLANK");
                                    break;
                                default:
                                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Scenario not found");
                            }
                            repo.save(s.current);
                        }, () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Scenario not found");
                        });
    }

}

// repo.findByLXXX(var).ifPresentOrElse(
// s -> {
// log.info("blah blah" + var);
// service.method(s, var);
// }, () -> {
// throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Strategy not
// found");
// });