# participants-ms

The Participants API Microservice for managing users in the New Beginnings clinical trial.

## Packaging and running the application

The application requires Maven and Java 11.

The application can be packaged using:
```shell script
mvn clean package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.

The application is now runnable using:
```shell script
java -jar target/quarkus-app/quarkus-run.jar
```

## Testing the application

The application is served on `http://localhost:8080`

A Swagger UI is provided at http://localhost:8080/q/swagger-ui to test API calls.
