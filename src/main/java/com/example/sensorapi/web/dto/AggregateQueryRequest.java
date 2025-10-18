package com.example.sensorapi.web.dto;

import java.time.Instant;
import java.util.List;

/*
    used by '/api/queries/aggregate' to define query parameters
 */

public class AggregateQueryRequest {
    private List<String> sensorIds;
    private List<String> metrics;
    private String statistic; //min, max, sum, average
    private Instant to;
    private Instant from;

    public List<String> getSensorIds() { return sensorIds; }
    public void setSensorIds(List<String> sensorIds) { this.sensorIds = sensorIds; }

    public List<String> getMetrics() { return metrics; }
    public void setMetrics(List<String> metrics) { this.metrics = metrics; }

    public String getStatistic() { return statistic; }
    public void setStatistic(String statistic) { this.statistic = statistic; }

    public Instant getTo() { return to; }
    public void setTo(Instant to) { this.to = to; }

    public Instant getFrom() { return from; }
    public void setFrom(Instant from) { this.from = from; }
}
