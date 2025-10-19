# API Weather Service

Quick Spring Boot app I threw together for tracking sensor data.

## Running it
```bash
mvn spring-boot:run

or 

run SensorApiApplication
```

Then hit http://localhost:8080

## Notes
- Using H2 in-memory DB for now (data doesn't persist between restarts)
- Frontend is pretty basic but functional
- Query API lets you get min/max/avg/sum over time ranges

## TODO
- Add proper validation
- Maybe add some scheduled tasks to generate fake data