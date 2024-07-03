FROM maven:3.9-eclipse-temurin-21-jammy AS build
WORKDIR /opt/activity-planning-service
COPY pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean package -Dmaven.test.skip=true


FROM eclipse-temurin:21
WORKDIR /opt/user-service
COPY --from=build /opt/activity-planning-service/target/activity-planning-service-0.0.1-SNAPSHOT.jar ./activity-planning-service.jar
EXPOSE 8084
CMD ["java", "-jar", "activity-planning-service.jar"]