package com.example.googleit.controller;

import com.example.googleit.repository.SubmissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@Slf4j
@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public String healthCheck() {
        log.info("health check invoked at timezone :" + ZonedDateTime.now());
        return "OK";
    }
}
