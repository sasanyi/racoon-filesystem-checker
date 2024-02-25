FROM docker.io/openjdk:17-jdk-alpine
RUN addgroup -S racoonsw && adduser -S racoonfc -G racoonsw
USER racoonfc:racoonsw
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

