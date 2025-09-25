FROM openjdk:17-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY . .

# Compila il progetto saltando i test
RUN mvn package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/pizzeria-0.0.1-SNAPSHOT.jar"]
