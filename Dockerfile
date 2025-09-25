# Usa un'immagine Java ufficiale
FROM openjdk:17-jdk-slim

# Crea una cartella per l'app
WORKDIR /app

# Copia il file .jar nel container
COPY target/pizzeria.jar app.jar

# Espone la porta 8080
EXPOSE 8080

# Comando per avviare l'app
CMD ["java", "-jar", "app.jar"]
