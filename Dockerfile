FROM openjdk:18-alpine
WORKDIR /app
COPY target/passgenie-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]