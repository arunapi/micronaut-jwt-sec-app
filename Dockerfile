FROM openjdk:14-alpine
COPY build/libs/micronaut-jwt-sec-app-*-all.jar micronaut-jwt-sec-app.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "micronaut-jwt-sec-app.jar"]