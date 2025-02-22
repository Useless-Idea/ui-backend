FROM maven:3.9-amazoncorretto-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-jdk

WORKDIR /app
COPY --from=build /app/target/ui-backend-0.0.1-SNAPSHOT.jar .

ENV ALLOWED_ORIGINS=${ALLOWED_ORIGINS}

CMD ["java", "-jar", "ui-backend-0.0.1-SNAPSHOT.jar"]