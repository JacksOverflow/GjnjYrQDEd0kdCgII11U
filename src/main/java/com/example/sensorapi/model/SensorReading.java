package com.example.sensorapi.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "sensor_reading")
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sensor_id", nullable = false)
    private String sensorId;

    @Column(name = "metric", nullable = false)
    private String metric;

    @Column(name = "reading_value", nullable = false)
    private double value;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    public SensorReading() {}

    public SensorReading(String sensorId, String metric, double value, Instant timestamp){
        this.sensorId = sensorId;
        this.metric = metric;
        this.value = value;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
