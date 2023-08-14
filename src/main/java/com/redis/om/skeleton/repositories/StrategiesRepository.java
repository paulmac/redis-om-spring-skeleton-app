package com.redis.om.skeleton.repositories;

import java.util.Optional;

import com.redis.om.skeleton.json.Strategy;
import com.redis.om.spring.repository.RedisDocumentRepository;

public interface StrategiesRepository extends RedisDocumentRepository<Strategy, String> {
    // Find strategy by its long and short name
    Iterable<Strategy> findByShortName(String shortName);

    Optional<Strategy> findByLongName(String longName);
}
