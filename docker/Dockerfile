FROM maven:3.9-amazoncorretto-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-jdk

WORKDIR /app
COPY --from=build /app/target/ui-backend-0.0.1-SNAPSHOT.jar .

ENV ALLOWED_ORIGINS=${ALLOWED_ORIGINS}
ENV POSTGRES_URL=${POSTGRES_URL}
ENV POSTGRES_PORT=${POSTGRES_PORT}
ENV POSTGRES_DB=${POSTGRES_DB}
ENV POSTGRES_USERNAME=${POSTGRES_USERNAME}
ENV POSTGRES_PASSWORD=${POSTGRES_PASSWORD}

CMD ["java", "-jar", "ui-backend-0.0.1-SNAPSHOT.jar"]