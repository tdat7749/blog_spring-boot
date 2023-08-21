#maven
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src/
COPY .mvn ./.mvn/

RUN mvn clean install -DskipTests=true


#spring app
FROM eclipse-temurin:17-jdk-jammy
##
WORKDIR /app
##
COPY --from=build /app/target/*.jar app.jar
##
ENTRYPOINT ["java","-jar","app.jar"]


