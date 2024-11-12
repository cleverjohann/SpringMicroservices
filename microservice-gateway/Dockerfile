# Dockerfile para microservice-config

# Imagen base de OpenJDK para ejecutar la aplicación Java
FROM openjdk:17-jdk-alpine

# Crear un directorio en el contenedor para la aplicación
WORKDIR /app

# Copiar el archivo JAR generado al contenedor
COPY target/microservice-gateway-0.0.1-SNAPSHOT.jar app.jar


# Exponer el puerto en el que el Config Server correrá (8888 por defecto)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]