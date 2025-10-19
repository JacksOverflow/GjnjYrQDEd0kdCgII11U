package com.example.sensorapi.controller;

import com.example.sensorapi.entity.SensorReading;
import com.example.sensorapi.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;


/*
    Defines REST endpoint for adding sensor readings
    Expose API and translate HTTP to service calls.
 */

@RestController
@RequestMapping("/api/sensors")
@CrossOrigin(origins = "*")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/{sensorId}/readings")
    public ResponseEntity<SensorReading> addReading(
            @PathVariable String sensorId,
            @Valid @RequestBody SensorReading reading) {

        reading.setSensorId(sensorId);

        if (reading.getTimestamp() == null) {
            reading.setTimestamp(Instant.now());
        }

        SensorReading saved = sensorService.addReading(reading);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{sensorId}/readings")
    public ResponseEntity<List<SensorReading>> getReadings(@PathVariable String sensorId) {
        return ResponseEntity.ok(sensorService.getReadingsBySensor(sensorId));
    }

    @GetMapping("/query")
    public ResponseEntity<?> querySensors(
            @RequestParam(required = false) String sensorId,
            @RequestParam(required = false) String metric,
            @RequestParam(defaultValue = "avg") String statistic,
            @RequestParam(required = false) Integer days){

        int daysBack = (days == null || days < 1) ? 1 : days;

        var result = sensorService.queryAggregate(sensorId, metric, statistic, daysBack);
        return ResponseEntity.ok(result);
    }
}
