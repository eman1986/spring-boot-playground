FROM eclipse-temurin:21-jdk-alpine

EXPOSE 8080

RUN mkdir /app

COPY build/libs/*.jar /app/webapp.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ENTRYPOINT ["java","-jar","/app/webapp.jar"]
