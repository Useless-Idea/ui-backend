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

ENV RABBIT_MQ_HOST=${RABBIT_MQ_HOST}
ENV RABBIT_MQ_PORT=${RABBIT_MQ_PORT}
ENV RABBIT_MQ_USERNAME=${RABBIT_MQ_USERNAME}
ENV RABBIT_MQ_PASSWORD=${RABBIT_MQ_PASSWORD}
ENV RABBIT_MQ_AUTO_STARTUP=${RABBIT_MQ_AUTO_STARTUP}

ENV EVE_SSO_CLIENT_ID=${EVE_SSO_CLIENT_ID}
ENV EVE_SSO_SECRET=${EVE_SSO_SECRET}
ENV EVE_SSO_CALLBAC=${EVE_SSO_CALLBACK}

ENV REDIS_HOST=${REDIS_HOST}
ENV REDIS_PORT=${REDIS_PORT}
ENV REDIS_PASSWORD=${REDIS_PASSWORD}

CMD ["java", "-jar", "ui-backend-0.0.1-SNAPSHOT.jar"]