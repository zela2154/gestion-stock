FROM openjdk:17-jdk

WORKDIR /app

COPY target/Stock-1.0.0.jar /app/Stock.jar

EXPOSE 8080

CMD ["java", "-jar", "Stock.jar"]