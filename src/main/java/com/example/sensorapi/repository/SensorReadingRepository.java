package com.example.sensorapi.repository;

import com.example.sensorapi.entity.SensorReading;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {

    List<SensorReading> findBySensorId(String sensorId);
    List<SensorReading> findBySensorIdAndMetricAndTimestampBetween( String sensorId, String metric, Instant from, Instant to);
    List<SensorReading> findBySensorIdAndTimestampBetween( String sensorId, Instant from, Instant to);
    List<SensorReading> findByMetricAndTimestampBetween( String metric, Instant from, Instant to);
    List<SensorReading> findByTimestampBetween(Instant from, Instant to);

}
