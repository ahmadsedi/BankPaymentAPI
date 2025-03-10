FROM openjdk:17-jdk-slim-buster
WORKDIR /app

COPY ./target/*.jar /app/build/app.jar

WORKDIR /app/build
ENTRYPOINT java -jar app.jar