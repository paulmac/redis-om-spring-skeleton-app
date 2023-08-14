package com.redis.om.skeleton.repositories;

import java.util.Optional;

import com.redis.om.skeleton.json.Scenario;
import com.redis.om.spring.repository.RedisDocumentRepository;

public interface ScenariosRepository extends RedisDocumentRepository<Scenario, String> {

    // Optional<Scenario> findByLongNameAndSymbolAndTimeFrameAndDirection(String
    // longName, String symbol,
    // Scenario.TimeFrame timeFrame, Scenario.Direction direction

    Iterable<Scenario> findByLongName(String longName);

    Optional<Scenario> findByLongNameAndSymbolAndTimeFrameAndDirection(String longName, String symbol,
            Scenario.TimeFrame timeFrame, Scenario.Direction direction);

}
