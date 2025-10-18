package com.example.sensorapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
    Entry point for the application
    EnableScheduling allows for background tasks, i.e. updating fake sensor data
 */

@SpringBootApplication
@EnableScheduling
public class SensorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorApiApplication.class, args);
    }
}
