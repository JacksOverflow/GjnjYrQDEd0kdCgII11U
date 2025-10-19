package com.example.sensorapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.Instant;

@Entity
@Table(name = "sensor_reading")
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sensor_id", nullable = false, length = 20)
    @NotBlank(message = "Sensor ID is required")
    @Size(max = 20, message = "Sensor ID too long")
    private String sensorId;

    @Column(name = "metric", nullable = false, length = 30)
    @NotBlank(message = "Metric is required")
    @Pattern(regexp = "[a-z_]+", message = "Metric must be lowercase letters and underscores")
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
