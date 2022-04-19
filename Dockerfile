FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD secure-connect.zip .
COPY target/mafia-user-server-demo-0.0.1-SNAPSHOT.jar /demo.jar
ENTRYPOINT ["java", "-XX:+UseContainerSupport","-Xmx256m", "-Xss512k","-XX:MetaspaceSize=100m", "-jar", "/demo.jar"]