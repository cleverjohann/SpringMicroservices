# Usar una imagen base de Java
FROM openjdk:17-jdk-alpine

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR del servidor Eureka
COPY target/microservice-eureka-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponer el puerto 8761, que es el puerto por defecto de Eureka
EXPOSE 8761

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
