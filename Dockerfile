FROM amazoncorretto:21

COPY build/libs/car-rent-app-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]