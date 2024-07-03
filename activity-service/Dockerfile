FROM maven:3.9-eclipse-temurin-21-jammy AS build
WORKDIR /opt/activity-service
COPY pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean package -Dmaven.test.skip=true


FROM eclipse-temurin:21
WORKDIR /opt/activity-service
COPY --from=build /opt/activity-service/target/activity-service-0.0.1-SNAPSHOT.jar ./activity-service.jar
EXPOSE 8081
CMD ["java", "-jar", "activity-service.jar"]