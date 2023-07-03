FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} transaction-service.jar
ENTRYPOINT ["java", "-jar", "transaction-service.jar"]