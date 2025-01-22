FROM openjdk:21-slim
LABEL authors="Grégoire Beaugnier"

ENV SPRING_PROFILES_ACTIVE demo

WORKDIR /var/opt/demo
COPY target/spring-batch-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","./app.jar"]