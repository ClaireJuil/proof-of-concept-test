FROM maven:3-jdk-11 AS build
COPY settings-docker.xml /usr/share/maven/ref/
COPY pom.xml /usr/src/app/pom.xml
COPY cg-mongo-client-api /usr/src/app/cg-mongo-client-api
COPY rass-mongo-client-api /usr/src/app/rass-mongo-client-api
COPY dam-api /usr/src/app/dam-api
RUN mvn -f /usr/src/app/pom.xml -gs /usr/share/maven/ref/settings-docker.xml clean package

FROM openjdk:11-slim-buster
COPY --from=build /usr/src/app/dam-api/target/dam-api-*.jar /usr/app/dam-api.jar
USER daemon
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/dam-api.jar"]
