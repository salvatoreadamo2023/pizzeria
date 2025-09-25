FROM openjdk:17-jdk-slim

WORKDIR /app

# Installa Maven
RUN apt-get update && apt-get install -y maven

# Copia tutto il progetto
COPY . .

# Compila il progetto
RUN mvn package

# Espone la porta
EXPOSE 8080

# Avvia l'app
CMD ["java", "-jar", "target/pizzeria-0.0.1-SNAPSHOT.jar"]
