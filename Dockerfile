# Dockerfile para ambiente de build da aplicação
FROM ubuntu:latest AS build
WORKDIR /app
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
# RUN mvn clean install
RUN mvn clean package

# Segundo estágio do Dockerfile para montar o container de execução da aplicação
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/labspringmvc-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]