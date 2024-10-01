
# Guia de ejecucion

Esta es la secuencia de ejecucion para el proyecto microservicios



## Microservicios

- Microservicio Config

- Microservicio Eureka
- Microservicios del Proyecto
- Microservicio Gateway 


## Installation de base de Datos Docker

Ejecucion de docker en terminal

```bash
  docker-compose up -d
```
## Pasos para actualizar:
Compilar el JAR: Después de cada cambio en tu código, necesitas volver a generar el archivo JAR:

bash
```bash
mvn clean package
```
Construir la imagen Docker: Una vez que el JAR esté listo, debes reconstruir la imagen Docker para incluir los cambios:

bash
```bash
docker build -t microservice-config:latest .
docker build -t cleverjohann/microservice-config:latest .
docker push cleverjohann/microservice-config:latest
```
Actualizar la imagen en Kubernetes: Después de construir la imagen, Kubernetes necesita la nueva versión. Tienes dos opciones:

Opción 1: Cambiar el tag de la imagen (recomendado): Cambia el tag de la imagen (por ejemplo, a 1.1 o latest) y actualiza tu despliegue en Kubernetes con el nuevo tag en tu archivo YAML. Luego ejecuta:

bash
```bash
kubectl apply -f config-deployment.yaml
```
Opción 2: Usar el mismo tag y reiniciar el pod: Si no cambias el tag, debes forzar a Kubernetes a reiniciar los pods para cargar la nueva imagen:

bash
```bash
kubectl rollout restart deployment config-server
```