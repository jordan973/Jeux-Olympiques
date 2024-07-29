# Étape 1 : Utiliser une image Maven pour construire le projet
FROM maven:3.8.1-openjdk-11 AS build
WORKDIR /app
COPY backend/pom.xml .
COPY backend/src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Utiliser une image OpenJDK pour exécuter l'application
FROM openjdk:11-jre-slim
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
