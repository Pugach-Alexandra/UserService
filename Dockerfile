FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD secure-connect-users.zip .
COPY target/Mafia-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]