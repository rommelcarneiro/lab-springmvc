# Dockerfile para ambiente de build da aplicação
FROM maven:3.8.4-jdk-11 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Segundo estágio do Dockerfile para montar o container de execução da aplicação
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/labspringmvc-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]