FROM maven:3.6.3-openjdk-17-slim AS MAVEN_BUILD
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn clean package

FROM openjdk:17-jdk-slim-buster
EXPOSE 8080
COPY --from=MAVEN_BUILD target/rest-offers-*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]