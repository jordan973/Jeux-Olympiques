# Utiliser une image de base officielle de Java runtime
FROM openjdk:11-jre-slim

# Ajouter un volume pour les fichiers temporaires
VOLUME /tmp

# Copier le fichier JAR dans l'image Docker
COPY backend/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port de l'application
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
