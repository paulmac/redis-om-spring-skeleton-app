package com.redis.om.skeleton.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.redis.om.skeleton.json.Alert;
import com.redis.om.skeleton.repositories.ScenariosRepository;
import com.redis.om.skeleton.repositories.TradesRepository;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Service
// @DependsOn("ContractManagerService")
public class AlertService {

    @NonNull
    private TradesRepository repo;

    @NonNull
    private ScenariosRepository scenariosRepo;

    // @NonNull
    // private OrderManagerService orderService;

    // @NonNull
    // private ContractManagerService contractService;

    public void post(Alert alert) {

        scenariosRepo.findByLongNameAndSymbolAndTimeFrameAndDirection(alert.getStrategy(), alert.getSymbol(),
                alert.getTimeFrame(), alert.getDirection()).ifPresentOrElse(
                        s -> {
                            log.info("Found Scenario : " + alert.getStrategy());
                            // Contract contract =
                            // s.getConId()contractService.getContractHolder(s.getConId()).getContract();
                            // switch (s.current.state) {
                            // case OPEN:
                            // if (StringUtils.startsWithIgnoreCase(alert.getDescription(), "Close")) {
                            // s.current.setExitAlertPrice(alert.getPrice());
                            // s.current.setExitAlertDateTime(alert.getTimeStamp());
                            // orderService.placeMarketOrder(contract, alert.getAction(),
                            // alert.getQuantity());
                            // } else
                            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trade not OPEN");
                            // break;
                            // case BLANK:
                            // if (StringUtils.startsWithIgnoreCase(alert.getDescription(), "Open")) {
                            // s.current.setEntryAlertPrice(alert.getPrice());
                            // s.current.setEntryAlertDateTime(alert.getTimeStamp());
                            // } else
                            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trade not BLANK");
                            // break;
                            // default:
                            // throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Scenario not
                            // found");
                            // }
                            // repo.save(s.current);
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