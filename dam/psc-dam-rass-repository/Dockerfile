FROM maven:3-jdk-11 AS build
COPY settings-docker.xml /usr/share/maven/ref/
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
COPY .openapi-generator-ignore /usr/src/app
RUN mvn -f /usr/src/app/pom.xml -gs /usr/share/maven/ref/settings-docker.xml clean package

FROM openjdk:11-slim-buster
COPY --from=build /usr/src/app/target/rass-structure-api-*.jar /usr/app/rass-structure-api.jar
USER daemon
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/rass-structure-api.jar"]
