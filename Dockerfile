FROM gradle:8.4-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]