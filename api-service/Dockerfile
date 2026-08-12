FROM maven:3.9.11-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY api-service/pom.xml api-service/pom.xml
COPY stream-service/pom.xml stream-service/pom.xml
RUN mvn -q -pl api-service -am dependency:go-offline
COPY api-service/src api-service/src
RUN mvn -q -pl api-service -am package -DskipTests
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/api-service/target/api-service-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
