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
    @NonNull
    ScenariosRepository repo;

    @DeleteMapping("all")
    void deleteAll() {
        repo.deleteAll();
    }

}
