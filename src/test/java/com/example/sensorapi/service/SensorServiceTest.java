package com.example.sensorapi.service;

import com.example.sensorapi.model.SensorReading;
import com.example.sensorapi.repository.SensorReadingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // rollback after each test
class SensorServiceTest {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SensorReadingRepository repository;

    @BeforeEach
    void setUp() {
        // clean slate for each test
        repository.deleteAll();
    }

    @Test
    void testAddReading() {
        //ARRANGE
        SensorReading reading = new SensorReading(
                "sensor-1",
                "temperature",
                25.5,
                Instant.now()
        );

        //ACT
        SensorReading saved = sensorService.addReading(reading);

        //ASSERT
        assertNotNull(saved.getId());
        assertEquals("sensor-1", saved.getSensorId());
        assertEquals("temperature", saved.getMetric());
        assertEquals(25.5, saved.getValue(), 0.01);
    }

    @Test
    void testGetReadingsBySensor() {
        //ARRANGE
        sensorService.addReading(new SensorReading("sensor-1", "temperature", 20.0, Instant.now()));
        sensorService.addReading(new SensorReading("sensor-1", "humidity", 65.0, Instant.now()));
        sensorService.addReading(new SensorReading("sensor-2", "temperature", 22.0, Instant.now()));

        //ACT
        List<SensorReading> readings = sensorService.getReadingsBySensor("sensor-1");

        //ASSERT
        assertEquals(2, readings.size());
        assertTrue(readings.stream().allMatch(r -> r.getSensorId().equals("sensor-1")));
    }

    @Test
    void testQueryAggregateAverage() {
        //ARRANGE
        Instant now = Instant.now();
        sensorService.addReading(new SensorReading("sensor-1", "temperature", 20.0, now));
        sensorService.addReading(new SensorReading("sensor-1", "temperature", 30.0, now));
        sensorService.addReading(new SensorReading("sensor-1", "temperature", 25.0, now));

        //ACT
        Map<String, Object> result = sensorService.queryAggregate("sensor-1", "temperature", "avg", 1);

        //ASSERT
        assertEquals(25.0, (Double) result.get("result"), 0.01);
        assertEquals(3, result.get("count"));
    }

    @Test
    void testQueryAggregateMin() {
        Instant now = Instant.now();
        sensorService.addReading(new SensorReading("sensor-1", "temperature", 20.0, now));
        sensorService.addReading(new SensorReading("sensor-1", "temperature", 30.0, now));
        sensorService.addReading(new SensorReading("sensor-1", "temperature", 15.0, now));

        Map<String, Object> result = sensorService.queryAggregate("sensor-1", "temperature", "min", 1);

        assertEquals(15.0, (Double) result.get("result"), 0.01);
    }

    @Test
    void testQueryAggregateMax() {
        Instant now = Instant.now();
        sensorService.addReading(new SensorReading("sensor-1", "temperature", 20.0, now));
        sensorService.addReading(new SensorReading("sensor-1", "temperature", 30.0, now));

        Map<String, Object> result = sensorService.queryAggregate("sensor-1", "temperature", "max", 1);

        assertEquals(30.0, (Double) result.get("result"), 0.01);
    }

    @Test
    void testQueryAggregateSum() {
        Instant now = Instant.now();
        sensorService.addReading(new SensorReading("sensor-1", "pressure", 1000.0, now));
        sensorService.addReading(new SensorReading("sensor-1", "pressure", 1010.0, now));
        sensorService.addReading(new SensorReading("sensor-1", "pressure", 990.0, now));

        Map<String, Object> result = sensorService.queryAggregate("sensor-1", "pressure", "sum", 1);

        assertEquals(3000.0, (Double) result.get("result"), 0.01);
    }

    @Test
    void testQueryWithNoResults() {
        Map<String, Object> result = sensorService.queryAggregate("nonexistent", "temperature", "avg", 1);

        assertTrue(result.containsKey("message"));
        assertEquals("No readings found", result.get("message"));
    }

    // TODO: test date range filtering
    // TODO: test querying across all sensors (null sensorId)
}