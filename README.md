# API Weather Service

Quick Spring Boot app I threw together for tracking sensor data.

## Running it
```bash
mvn spring-boot:run

or 

run SensorApiApplication
```

Then hit http://localhost:8080

**Query Parameters:**
- `sensorId` (optional): Filter by specific sensor or leave blank for all
- `metric` (optional): temperature, humidity, or pressure (or blank for all)
- `statistic` (optional): avg (default), min, max, sum
- `days` (optional): Days to look back, **1-31 days** (default: 1)

## Notes
- Using H2 in-memory DB for now (data doesn't persist between restarts)
- Frontend is pretty basic but functional
- Query API lets you get min/max/avg/sum over time ranges

## TODO
- Maybe add some scheduled tasks to generate fake data
- Extend test coverage to Repository/recency
- Migrate to DTO-usage to be able to update DB without breaking APIs