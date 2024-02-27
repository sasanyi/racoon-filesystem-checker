FROM docker.io/openjdk:17-jdk-alpine

ARG APP_USER

RUN addgroup -S racoonsw && adduser -S ${APP_USER} -G racoonsw
USER ${APP_USER}:racoonsw
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

