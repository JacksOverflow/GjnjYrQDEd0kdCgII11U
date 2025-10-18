package com.example.sensorapi.web.dto;

/*
    Defines what the aggregate API returns for eac metric per sensor
 */

public class AggregateResultDto {
    private String sensorId;
    private String metric;
    private String statistic;
    private double value;

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }

    public String getStatistic() { return statistic; }
    public void setStatistic(String statistics) { this.statistic = statistic; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
}
