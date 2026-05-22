FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY backend-1.0.0.jar app.jar
RUN touch .env

EXPOSE 9019
ENTRYPOINT ["java", "-jar", "app.jar"]
