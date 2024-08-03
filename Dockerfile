FROM maven:3.8.4-openjdk-8 as builder
WORKDIR /app
COPY . /app/.
RUN mvn clean install

FROM amazoncorretto:8
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/*.jar"]

