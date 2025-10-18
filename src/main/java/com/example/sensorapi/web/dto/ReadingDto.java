package com.example.sensorapi.web.dto;

import java.time.Instant;


/*
    used by '/api/sensors/{id}/readings' to post new sensor data
 */

public class ReadingDto {
    private String metric;
    private double value;
    private Instant timestamp;


    //getters and setters
    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
