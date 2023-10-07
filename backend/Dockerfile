#maven
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

COPY .mvn/ .mvn

COPY mvnw pom.xml ./

RUN mvn dependency:go-offline

COPY src ./src

CMD ["mvn","spring-boot:run"]

#RUN mvn clean install -DskipTests=true


#spring app
# for deploy
#FROM eclipse-temurin:17-jdk-jammy
###
#WORKDIR /app
###
#COPY --from=build /app/target/*.jar app.jar
###
#ENTRYPOINT ["java","-jar","app.jar"]


