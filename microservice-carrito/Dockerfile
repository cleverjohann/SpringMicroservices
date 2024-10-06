# Etapa de construcción
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Imagen final
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/microservice-carrito-1.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
