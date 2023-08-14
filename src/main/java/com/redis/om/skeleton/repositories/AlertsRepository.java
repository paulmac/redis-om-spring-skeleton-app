package com.redis.om.skeleton.repositories;

import com.redis.om.skeleton.json.Alert;
import com.redis.om.spring.repository.RedisDocumentRepository;

public interface AlertsRepository extends RedisDocumentRepository<Alert, String> {

}
