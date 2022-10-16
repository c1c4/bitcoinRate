FROM maven:3.8.6-openjdk-11-slim AS MAVEN_BUILD

COPY ./ ./

RUN mvn clean package

FROM openjdk:11-jre-slim

COPY --from=MAVEN_BUILD /target/bitcoinRate-1.0.jar /bitcoinRate.jar

CMD ["java", "-jar", "/bitcoinRate.jar"]