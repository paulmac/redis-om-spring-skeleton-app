package com.redis.om.skeleton.repositories;

import com.redis.om.skeleton.json.Trade;
import com.redis.om.spring.repository.RedisDocumentRepository;

public interface TradesRepository extends RedisDocumentRepository<Trade, String> {

}
