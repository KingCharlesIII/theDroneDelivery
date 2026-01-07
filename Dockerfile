
FROM maven:3.9.9-amazoncorretto-21-debian AS build

WORKDIR /app


COPY pom.xml .
COPY src ./src


RUN mvn clean package -DskipTests

FROM gcr.io/distroless/java21-debian12

WORKDIR /app

COPY --from=build /app/target/ilp_submission_2-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080

ENV ILP_ENDPOINT=https://ilp-rest-2025-bvh6e9hschfagrgy.ukwest-01.azurewebsites.net

# Run the application
ENTRYPOINT ["java", "-jar", "./app.jar"]
