FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} card-service.jar
ENTRYPOINT ["java", "-jar", "card-service.jar"]