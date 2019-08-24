FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests
# There cannot be -DskipTest- try to fix it


FROM openjdk:11-jdk-oracle
VOLUME /tmp
ARG JAR_FILE
COPY --from=build /home/app/target/event-storming-classic.jar app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=production","-jar","/app.jar"]
