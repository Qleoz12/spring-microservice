FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} discovery.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "discovery.jar"]