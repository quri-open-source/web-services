# Etapa de construcción (Java 24 personalizado)
FROM eclipse-temurin:24-jdk AS maven-build-env

# Instala Maven manualmente
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY mvnw .
COPY .mvn ./.mvn

RUN mvn clean package -DskipTests

# Etapa de ejecución (Java 24)
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=maven-build-env /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]