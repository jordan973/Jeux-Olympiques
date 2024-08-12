# Étape 1 : Construire l'application React
FROM node:16 as frontend-build
WORKDIR /app
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ ./
RUN npm run build

# Étape 2 : Construire l'application Spring Boot
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY backend/pom.xml .
COPY backend/src ./src
RUN mvn clean package -DskipTests

# Étape 3 : Combiner les builds et démarrer Spring Boot
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar /app/app.jar

# Copier le build React dans le dossier static
COPY --from=frontend-build /app/build /app/static

EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
