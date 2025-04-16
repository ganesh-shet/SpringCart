# Stage 1: Use a custom JDK 23 base and install Maven manually
FROM eclipse-temurin:23-jdk AS build

WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    mvn -version

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY --from=build /app/target/springkart-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
