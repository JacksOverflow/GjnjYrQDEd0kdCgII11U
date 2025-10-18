package com.example.sensorapi.repository;

import com.example.sensorapi.model.SensorReading;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;


/*
    Provides CRUD and query methods for SensorReading objects.
    JpaRepository for save, find, delete, etc..

 */

@Repository
public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {

    List<SensorReading> findBySensorId(
            String sensorId);
    List<SensorReading> findBySensorIdAndMetricAndTimestampBetween(
            String sensorId,
            String metric,
            Instant from,
            Instant to);
    List<SensorReading> findBySensorIdAndTimestampBetween(
            String sensorId,
            Instant from,
            Instant to);
    List<SensorReading> findByMetricAndTimestampBetween(
            String metric,
            Instant from,
            Instant to);
    List<SensorReading> findByTimestampBetween(
            Instant from,
            Instant to);

    @Query(value = """
        SELECT sensor_id, metric,
            CASE WHEN :statistic = 'min' THEN MIN(value)
                WHEN :statistic = 'max' THEN MAX(value)
                WHEN :statistic = 'sum' THEN SUM(value)
                ELSE AVG(value) END as agg_value
        FROM sensor_reading
        WHERE timestamp BETWEEN :from AND :to
            AND metric IN :metrics
            AND (:useAllSensors = TRUE or sensor_id IN :sensorIds)
        GROUP BY sensor_id, metric
        """, nativeQuery = true)
    List<Object[]> aggregate(
            @Param("metrics") List<String> metrics,
            @Param("statistics") String statistics,
            @Param("from") Instant from,
            @Param("to") Instant to,
            @Param("useAllSensors") boolean useAllSensors,
            @Param("sensorIds") List<String> sensorIds);
}
