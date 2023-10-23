FROM eclipse-temurin:17.0.8.1_1-jre-jammy
WORKDIR /app
ADD target/warehouse-0.0.1-SNAPSHOT.jar /app/warehouse.jar
CMD ["java", "-jar", "/app/warehouse.jar"]