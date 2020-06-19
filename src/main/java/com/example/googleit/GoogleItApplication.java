package com.example.googleit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
public class GoogleItApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoogleItApplication.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		log.info("Started application in UTC timezone :" + ZonedDateTime.now());
	}
}
