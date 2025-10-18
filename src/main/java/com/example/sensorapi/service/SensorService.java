package com.example.sensorapi.service;

import com.example.sensorapi.model.SensorReading;
import com.example.sensorapi.repository.SensorReadingRepository;
import com.example.sensorapi.web.dto.*;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

/*
    Handles all general logic - saving readings and aggregating results
*/

@Service
public class SensorService {

    private final SensorReadingRepository repo;

    public SensorService(SensorReadingRepository repo){
        this.repo = repo;
    }

    public SensorReading addReading(SensorReading reading) {
        return repo.save(reading);
    }

    public List<SensorReading> getReadingsBySensor(String sensorId) {
        return repo.findBySensorId(sensorId);
    }

    public Map<String, Object> queryAggregate(String sensorId, String metric, String statistic, int daysBack) {
        Instant to = Instant.now();
        Instant from = to.minusSeconds(daysBack * 86400L);

        List<SensorReading> readings;

        if (sensorId != null && metric != null) {
            readings = repo.findBySensorIdAndMetricAndTimestampBetween(sensorId, metric, from, to);
        } else if (sensorId != null) {
            readings = repo.findBySensorIdAndTimestampBetween(sensorId, from, to);
        } else if (metric != null) {
            readings = repo.findByMetricAndTimestampBetween(metric, from, to);
        } else {
            readings = repo.findByTimestampBetween(from, to);
        }

        if (readings.isEmpty()) {
            return Map.of("message", "No readings found");
        }

        double result;
        switch (statistic.toLowerCase()) {
            case "min":
                result = readings.stream().mapToDouble(SensorReading::getValue).min().orElse(Double.NaN);
            case "max":
                result = readings.stream().mapToDouble(SensorReading::getValue).max().orElse(Double.NaN);
            case "sum":
                result = readings.stream().mapToDouble(SensorReading::getValue).sum();
            default:
                result = readings.stream().mapToDouble(SensorReading::getValue).average().orElse(Double.NaN);
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("sensorId", sensorId != null ? sensorId : "all");
        response.put("metric", metric != null ? metric : "all");
        response.put("statistic", statistic);
        response.put("daysBack", daysBack);
        response.put("result", result);
        response.put("count", readings.size());
        return response;
    }
}
